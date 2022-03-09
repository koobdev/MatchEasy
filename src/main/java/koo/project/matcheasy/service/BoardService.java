package koo.project.matcheasy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.board.RequestPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.dto.RecruitPositionDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.BoardContext;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.mapper.RecruitPositionMapper;
import koo.project.matcheasy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static koo.project.matcheasy.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final RecruitPositionRepository recruitPositionRepository;
    private final RequestPositionRepository requestPositionRepository;
    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardContext boardContext;
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;


    /**
     * 글 목록
     */
    public List<BoardDto> boardList(){

        List<BoardDto> returnList = new ArrayList<>();

        // Entity -> dto
        boardRepository.findAll()
                .forEach(content -> {
                    BoardDto toDto = BoardMapper.BOARD_MAPPER.toDto(content);
                    log.info("toDto toString ::: {}", toDto.toString());
                    returnList.add(toDto);
                });

        log.info("returnLIst ::: {}", returnList);

        return returnList;
    }


    /**
     * 글 작성
     */
    public ResponseEntity<OkResponse> registerContent(BoardDto boardDto) throws JsonProcessingException {

        duplicatedWriter(boardDto);

        // 게시글 상태의 Default는 0
        // status 0 : 모집 중 , status 1 : 모집 완료
        boardDto.setStatus(0);

        BoardContent content = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        log.info("toEntity : {}", content.toString());

        for (RecruitPositionDto recruitPosition : boardDto.getPositions()) {
            RecruitPosition position = RecruitPositionMapper.POSITION_MAPPER.toEntity(recruitPosition);
            position.addBoardContent(content);
        }


        // 채팅방 자동생성
        chatRoomService.createChatRoom(content);

        // 게시글 저장
        boardRepository.save(content);
        return OkResponse.toResponse(objectMapper.writeValueAsString(boardDto),"게시글을 등록하였습니다.");
    }

    /**
     * 글 열기
     */
    public BoardDto openContent(Long boardDetail){
        BoardContent findContent = boardRepository.findById(boardDetail);

        BoardDto boardDto = BoardMapper.BOARD_MAPPER.toDto(findContent);
        log.info("content DTO : {}", boardDto.toString());

        return boardDto;
    }


    /**
     * 글 열기 - userId로 찾기
     */
    public BoardDto openMyContent(Long userId){
        BoardContent findContent = boardRepository.findByWriterId(userId)
                .orElseGet(() -> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });

        BoardDto boardDto = BoardMapper.BOARD_MAPPER.toDto(findContent);
        log.info("content DTO : {}", boardDto.toString());

        return boardDto;
    }



    /**
     * 글 수정
     */
    public ResponseEntity<OkResponse> updateContent(Long id, BoardDto boardDto, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        duplicatedWriterWithRequest(id, loginId);

        log.info("boardDto positions :: {}", boardDto.getPositions());

        List<RecruitPosition> positions = new ArrayList<>();
        for (RecruitPositionDto recruitPosition : boardDto.getPositions()) {
            RecruitPosition position = RecruitPositionMapper.POSITION_MAPPER.toEntity(recruitPosition);
            positions.add(position);
        }

        BoardContent findContent = boardRepository.findById(id);
        findContent.updateContent(boardDto.getTitle(), boardDto.getContent(), positions);


        return OkResponse.toResponse(objectMapper.writeValueAsString(findContent),"게시글을 수정하였습니다.");
    }

    /**
     * 글 삭제
     */
    public ResponseEntity<OkResponse> deleteContent(Long id, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        duplicatedWriterWithRequest(id, loginId);

        BoardContent findContent = boardRepository.findById(id);
        boardRepository.delete(findContent);

        return OkResponse.toResponse(objectMapper.writeValueAsString(findContent),"게시글을 삭제하였습니다.");
    }


    /**
     * 지원하기
     * 1. 지원자가 팀을 가지고 있는지 체크
     * 2. 지원자가 해당 게시글의 작성자인지 체크 && 저장(지원)
     * @return
     */
    public ResponseEntity<OkResponse> recruit(Long positionId, String recruitMessage, HttpServletRequest request){
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        // 1
        memberRepository.findByLoginId(loginId)
                .ifPresent(m-> {
                    log.info("Check 1 :::: {}", m.getLoginId());
                    if(!Objects.isNull(m.getTeam())){
                        log.info("m.getTeam() is not Null !!!! -> Throw Custom Exception");
                        throw new CustomException(TEAM_DUPLICATED);
                    }
                });

        // 2
        recruitPositionRepository.findById(positionId)
                .ifPresent(r -> {

                    log.info("Check 2 :::: id : {}, position L {}", r.getId(), r.getPosition());
                    recruitWriterCheck(r.getBoardContent().getWriterId(), loginId);

                    RequestPosition requestPosition = RequestPosition.builder()
                            .status(0)
                            .recruitMessage(recruitMessage)
                            .build();
                    // 요청한 포지션에 requestPosition을 매핑함
                    requestPosition.addPosition(r);

                    // 1차 캐시에서 가져옴
                    // 지원 요청한 member를 requestPosition에 추가함
                    memberRepository.findByLoginId(loginId)
                            .ifPresent(requestPosition::addRecruitMember);

                    requestPositionRepository.save(requestPosition);
                });

        return OkResponse.toResponse("ok","포지션 지원을 완료하였습니다.");
    }

    /**
     * 지원자 목록
     * 포지션과 포지션 지원자를 join해서 물고옴
     */
    public List<RecruitPosition> recruitList(HttpServletRequest request){
        List<RecruitPosition> recruitPositionList = new ArrayList<>();
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseGet(() -> {
                    throw new CustomException(MEMBER_NOT_FOUND);
                });

        log.info("FIND MEMBER :::::::::: {} , {}", findMember.getLoginId(), findMember.getId());

        boardRepository.findByWriterId(findMember.getId())
                .ifPresentOrElse(content -> {

//                    for (RecruitPosition position : content.getPositions()) {
//                        if(position.getBoardContent().getId().equals(content.getId())
//                                && position.getStatus() == 1){
//                            recruitPositionList.add(position);
//                        }

                    recruitPositionList.addAll(content.getPositions());
                }, () -> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });

        return recruitPositionList;
    }


    public ResponseEntity<OkResponse> acceptOrReject(Long idx, int status, String message) {

        // status == 1 : 수락
        // status == 2 : 거절 - 거절 메세지를 등록함
        requestPositionRepository.findById(idx)
                .ifPresentOrElse(r -> {
                    if(status == 1){
                        r.updateStatus(status);
                        r.getPosition().updateStatus(1);
                    }else {
                        r.updateStatus(status);
                        r.updateRejectMessage(message);
                    }
                }, ()-> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });

        return OkResponse.toResponse("ok","완료하였습니다.");
    }


    /**
     * 지원자 - 게시글 작성자 체크
     * 지원자가 해당 게시글의 작성자인지 체크
     */
    private boolean recruitWriterCheck(Long writerId, String loginId) {
        if(memberRepository.findById(writerId).getLoginId().equals(loginId)){
            throw new CustomException(FAIL_WRITER_AUTHORIZED);
        }
        return true;
    }


    /**
     * 중복 작성자 찾기
     * 현재 작성된 글의 작성자와 로그인 정보를 체크
     */
    private void duplicatedWriterWithRequest(Long boardId, String name){
        BoardContent findContent = boardRepository.findById(boardId);
        Member writer = memberRepository.findById(findContent.getWriterId());

        if(!name.equals(writer.getLoginId())){
            throw new CustomException(FAIL_MEMBER_AUTHORIZED);
        }
    }

    /**
     * 중복 작성자 찾기
     * 작성자는 단 한개의 게시글만 등록할 수 있음
     */
    private void duplicatedWriter(BoardDto boardDto){
        Member writer = memberRepository.findById(boardDto.getWriterId());

        Optional<BoardContent> findDuplicatedWriter = boardRepository.findAll().stream()
                .filter(b -> b.getWriterId().equals(writer.getId()))
                .findAny();

        findDuplicatedWriter
                .ifPresent(b -> {
                    throw new CustomException(CONTENT_DUPLICATED);
                });
    }


}
