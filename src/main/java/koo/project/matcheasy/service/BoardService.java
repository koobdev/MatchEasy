package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.chat.ChatRoom;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.BoardContext;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public BoardDto registerContent(BoardDto boardDto){

        duplicatedWriter(boardDto);

        // 게시글 상태의 Default는 0
        // status 0 : 모집 중 , status 1 : 모집 완료
        boardDto.setStatus(0);

//        log.info("BEFORE CONVERT >>>>>>>>>>>>>>>>>>>>>>>");
//        log.info("boardDto title : {}, boardDto content : {}", boardDto.getTitle(), boardDto.getContent());

//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        log.info("{}", boardDto.toString());


        // TODO
        // MapStruct에서 Collection물고오는 객체들 처리에 대한 연구 필요

        BoardContent content = BoardMapper.BOARD_MAPPER.toEntity(boardDto, boardContext);
        log.info("toEntity : {}", content.toString());

//        List<RecruitPositionDto> positionList = boardDto.getPositionList();
//
//        for (RecruitPositionDto position : positionList) {
//            log.info("position : {}, content : {}", position.getPosition(), position.getContent());
////            content.addRecruitPosition(position);
//            RecruitPosition p = RecruitPositionMapper.POSITION_MAPPER.toEntity(position);
//            p.builder().boardContent(content);
//        }

//        for (RecruitPositionDto position : positions) {
//            RecruitPosition positionEntity = RecruitPositionMapper.POSITION_MAPPER.toEntity(position);
//            content.addRecruitPosition(positionEntity);
//        }




//        List<RecruitPosition> positionList = content.getPositionList();
//
//        for (RecruitPosition position : positionList) {
//            log.info("position : {}, content : {}", position.getPosition(), position.getContent());
//            content.addRecruitPosition(position);
//        }

//        log.info("AFTER CONVERT >>>>>>>>>>>>>>>>>>>>>>>");
//        log.info("board title : {}, board content : {}", content.getTitle(), content.getContent());



        // 채팅방 자동생성
        chatRoomService.createChatRoom(content);

        // 게시글 저장
        boardRepository.save(content);
        return boardDto;
    }

    /**
     * 글 열기
     */
    public BoardDto openContent(Long boardDetail){
        BoardContent findContent = boardRepository.findById(boardDetail);
        log.info(">>>>>>> getContent :: {}", findContent.toString());

        BoardDto boardDto = BoardMapper.BOARD_MAPPER.toDto(findContent);
        log.info(">>>>>>> contentToDto :: {}", boardDto);

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

        BoardContent boardEntity = BoardMapper.BOARD_MAPPER.toEntity(boardDto, boardContext);
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

        BoardContent boardEntity = BoardMapper.BOARD_MAPPER.toEntity(boardDto, boardContext);
        boardRepository.delete(boardEntity);
    }


    /**
     * 지원하기
     * 1. 지원자가 팀을 가지고 있는지 체크
     * 2. 지원자가 해당 게시글의 작성자인지 체크 && 저장(지원)
     */
    public void recruit(Long positionId, HttpServletRequest request){
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);


        // TODO CHECK FOR NULLPOINTER ERROR!!!!!!!!!!!!

        // 1
        memberRepository.findByLoginId(loginId)
                .ifPresent(m-> {
                    if(!Objects.isNull(m.getTeam())){
                        throw new CustomException(TEAM_DUPLICATED);
                    }
                });

        // 2
        recruitPositionRepository.findById(positionId)
                .ifPresent(r -> {
                    recruitWriterCheck(r.getBoardContent().getWriterId(), loginId);

                    // 1차 캐시에서 가져옴
                    // position에 member저장
                    memberRepository.findByLoginId(loginId)
                            .ifPresent(r::addRecruitMember);
                });
    }


    /**
     * 지원자 - 게시글 작성자 체크
     * 지원자가 해당 게시글의 작성자인지 체크
     */
    private void recruitWriterCheck(Long writerId, String loginId) {
        if(memberRepository.findById(writerId).getLoginId().equals(loginId)){
            throw new CustomException(FAIL_WRITER_AUTHORIZED);
        }
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
