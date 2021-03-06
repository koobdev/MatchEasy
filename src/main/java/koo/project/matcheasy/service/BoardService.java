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
import koo.project.matcheasy.dto.RequestPositionDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.BoardContext;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.mapper.RecruitPositionMapper;
import koo.project.matcheasy.mapper.RequestPositionMapper;
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
import java.util.stream.Collectors;

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
     * ??? ??????
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
     * ??? ??????
     */
    public ResponseEntity<OkResponse> registerContent(BoardDto boardDto) throws JsonProcessingException {

        duplicatedWriter(boardDto);

        // ????????? ????????? Default??? 0
        // status 0 : ?????? ??? , status 1 : ?????? ??????
        boardDto.setStatus(0);

        BoardContent content = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        log.info("toEntity : {}", content.toString());

        for (RecruitPositionDto recruitPosition : boardDto.getPositions()) {
            RecruitPosition position = RecruitPositionMapper.POSITION_MAPPER.toEntity(recruitPosition);
            position.addBoardContent(content);
        }


        // ????????? ????????????
        chatRoomService.createChatRoom(content);

        // ????????? ??????
        boardRepository.save(content);
        return OkResponse.toResponse(objectMapper.writeValueAsString(boardDto),"???????????? ?????????????????????.");
    }

    /**
     * ??? ??????
     */
    public BoardDto openContent(Long boardDetail){
        BoardContent findContent = boardRepository.findById(boardDetail);

        BoardDto boardDto = BoardMapper.BOARD_MAPPER.toDto(findContent);
        log.info("content DTO : {}", boardDto.toString());

        return boardDto;
    }


    /**
     * ??? ?????? - userId??? ??????
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
     * ??? ??????
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


        return OkResponse.toResponse(objectMapper.writeValueAsString(findContent),"???????????? ?????????????????????.");
    }

    /**
     * ??? ??????
     */
    public ResponseEntity<OkResponse> deleteContent(Long id, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        duplicatedWriterWithRequest(id, loginId);

        BoardContent findContent = boardRepository.findById(id);
        boardRepository.delete(findContent);

        return OkResponse.toResponse(objectMapper.writeValueAsString(findContent),"???????????? ?????????????????????.");
    }


    /**
     * ????????????
     * 1. ???????????? ?????? ????????? ????????? ??????
     * 2. ???????????? ?????? ???????????? ??????????????? ?????? && ??????(??????)
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
                    // ????????? ???????????? requestPosition??? ?????????
                    requestPosition.addPosition(r);

                    // 1??? ???????????? ?????????
                    // ?????? ????????? member??? requestPosition??? ?????????
                    memberRepository.findByLoginId(loginId)
                            .ifPresent(requestPosition::addRecruitMember);

                    requestPositionRepository.save(requestPosition);
                });

        return OkResponse.toResponse("ok","????????? ????????? ?????????????????????.");
    }

    /**
     * ????????? ??????
     * ???????????? ????????? ???????????? join?????? ?????????
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
                    recruitPositionList.addAll(content.getPositions());
                }, () -> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });

        return recruitPositionList;
    }


    /**
     * ?????? ?????? ??????
     * ?????? ????????? ?????????(RequestPosition)??? ?????? ?????????(RecruitPosition)??? ????????? BoardContent?????? join
     */
    public List<RequestPositionDto> myRecruitList(Long id){

        List<RequestPositionDto> list = new ArrayList<>();
        requestPositionRepository.findByMemberId(id)
                .forEach(r -> {
                    Long boardId = r.getPosition().getBoardContent().getId();
                    int status = r.getPosition().getBoardContent().getStatus();

                    RequestPositionDto requestPositionDto = RequestPositionMapper.REQUEST_POSITION_MAPPER.toDto(r);
                    requestPositionDto.updateBoardDto(boardId, status);
                    list.add(requestPositionDto);
                });

        return list;
    }


    public ResponseEntity<OkResponse> acceptOrReject(Long idx, int status, String message) {

        // status == 1 : ??????
        // status == 2 : ?????? - ?????? ???????????? ?????????
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

        return OkResponse.toResponse("ok","?????????????????????.");
    }


    /**
     * ????????? - ????????? ????????? ??????
     * ???????????? ?????? ???????????? ??????????????? ??????
     */
    private boolean recruitWriterCheck(Long writerId, String loginId) {
        if(memberRepository.findById(writerId).getLoginId().equals(loginId)){
            throw new CustomException(FAIL_WRITER_AUTHORIZED);
        }
        return true;
    }


    /**
     * ?????? ????????? ??????
     * ?????? ????????? ?????? ???????????? ????????? ????????? ??????
     */
    private void duplicatedWriterWithRequest(Long boardId, String name){
        BoardContent findContent = boardRepository.findById(boardId);
        Member writer = memberRepository.findById(findContent.getWriterId());

        if(!name.equals(writer.getLoginId())){
            throw new CustomException(FAIL_MEMBER_AUTHORIZED);
        }
    }

    /**
     * ?????? ????????? ??????
     * ???????????? ??? ????????? ???????????? ????????? ??? ??????
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
