package koo.project.matcheasy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
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

        return boardDto;
    }



    /**
     * 글 수정
     */
    public void updateContent(BoardDto boardDto, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        log.info("RequestToken LoginId ::::::: {}", loginId);

        duplicatedWriterWithRequest(boardDto, loginId);

        BoardContent boardEntity = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        boardRepository.save(boardEntity);
    }

    /**
     * 글 삭제
     */
    public void deleteContent(BoardDto boardDto, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        log.info("RequestToken LoginId ::::::: {}", loginId);

        duplicatedWriterWithRequest(boardDto, loginId);

        BoardContent boardEntity = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        boardRepository.delete(boardEntity);
    }


    /**
     * 지원하기
     * 1. 지원자가 팀을 가지고 있는지 체크
     * 2. 지원자가 해당 게시글의 작성자인지 체크 && 저장(지원)
     * @return
     */
    public ResponseEntity<OkResponse> recruit(Long positionId, HttpServletRequest request){
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

                    // 1차 캐시에서 가져옴
                    // position에 member저장 && status 1로 update(지원 상태)
                    memberRepository.findByLoginId(loginId)
                            .ifPresent(member -> {
                                r.addRecruitMember(member);
                                r.updateStatus(1);
                            });
                });

        return OkResponse.toResponse("ok","포지션 지원을 완료하였습니다.");
    }

    /**
     * 지원자 목록
     * 내가 작성한 글과 포지션 지원자를 join해서 물고옴
     */
    public JSONArray recruitList(HttpServletRequest request){
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
                    for (RecruitPosition position : content.getPositions()) {
                        if(position.getBoardContent().getId().equals(content.getId())
                                && position.getStatus() == 1){
                            recruitPositionList.add(position);
                        }
                    }
                }, () -> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });


        // 지원자 리스트 뽑아서 json 형식으로 리턴
        JSONArray jsonArray = new JSONArray();
        for (RecruitPosition position : recruitPositionList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("positionId" ,position.getId());
            jsonObject.put("content" ,position.getContent());
            jsonObject.put("position" ,position.getPosition());
            jsonObject.put("status" ,position.getStatus());

            JSONObject member = new JSONObject();
            member.put("id", position.getRecruitMember().getId());
            member.put("loginId", position.getRecruitMember().getLoginId());
            member.put("email", position.getRecruitMember().getEmail());
            member.put("age", position.getRecruitMember().getAge());
            member.put("name", position.getRecruitMember().getName());
            member.put("position", position.getRecruitMember().getPosition());

            jsonObject.put("recuritMember" ,member);
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }


    public ResponseEntity<OkResponse> acceptOrReject(Long idx, int status, String message) {

        recruitPositionRepository.findById(idx)
                .ifPresent(r -> {
                    r.updateStatus(status);
                    r.updateRejectMessage(message);
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
    private void duplicatedWriterWithRequest(BoardDto boardDto, String name) throws Exception {
        Member writer = memberRepository.findById(boardDto.getWriterId());

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
