package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.dto.RecruitPositionDto;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.mapper.RecruitPositionMapper;
import koo.project.matcheasy.repository.BoardRepository;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 글 작성
     */
    public BoardDto registerContent(BoardDto boardDto){

        duplicatedWriter(boardDto);

//        log.info("BEFORE CONVERT >>>>>>>>>>>>>>>>>>>>>>>");
//        log.info("boardDto title : {}, boardDto content : {}", boardDto.getTitle(), boardDto.getContent());

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("{}", boardDto.toString());


        // TODO
        // MapStruct에서 Collection물고오는 객체들 처리에 대한 연구 필요

        @NotEmpty List<RecruitPositionDto> positions = boardDto.getPositions();

        BoardContent content = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        log.info("toEntity : {}", content.toString());

        for (RecruitPositionDto position : positions) {
            RecruitPosition positionEntity = RecruitPositionMapper.POSITION_MAPPER.toEntity(position);
            content.addRecruitPosition(positionEntity);
        }




//        List<RecruitPosition> positionList = content.getPositionList();
//
//        for (RecruitPosition position : positionList) {
//            log.info("position : {}, content : {}", position.getPosition(), position.getContent());
//            content.addRecruitPosition(position);
//        }

//        @NotEmpty List<RecruitPosition> pList = boardDto.getPositions();
//
//        for (RecruitPosition position : pList) {
//            log.info("position : {}, content : {}", position.getPosition(), position.getContent());
//            content.addRecruitPosition(position);
//        }

//        log.info("AFTER CONVERT >>>>>>>>>>>>>>>>>>>>>>>");
//        log.info("board title : {}, board content : {}", content.getTitle(), content.getContent());

        boardRepository.save(content);

        return boardDto;
    }


    /**
     * 글 수정
     */
    public void updateContent(BoardDto boardDto, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String name = jwtTokenProvider.getSubject(token);

        log.info("RequestToken UserName ::::::: {}", name);

        duplicatedWriterWithRequest(boardDto, name);

        BoardContent boardEntity = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        boardRepository.save(boardEntity);
    }

    /**
     * 글 삭제
     */
    public void deleteContent(BoardDto boardDto, HttpServletRequest request) throws Exception {
        String token = authExtractor.extract(request, "Bearer");
        String name = jwtTokenProvider.getSubject(token);

        log.info("RequestToken UserName ::::::: {}", name);

        duplicatedWriterWithRequest(boardDto, name);

        BoardContent boardEntity = BoardMapper.BOARD_MAPPER.toEntity(boardDto);
        boardRepository.delete(boardEntity);
    }



    /**
     * 중복 작성자 찾기
     * 현재 작성된 글의 작성자와 로그인 정보를 체크
     */
    private void duplicatedWriterWithRequest(BoardDto boardDto, String name) throws Exception {
        Member writer = memberRepository.findById(boardDto.getWriterId());

        if(!boardDto.getWriterId().equals(writer.getId())){
            throw new Exception("글 작성자만 수정 가능합니다.");
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
                    try {
                        throw new Exception("이미 작성된 게시글이 존재합니다.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
