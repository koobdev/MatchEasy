package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.mapper.BoardMapper;
import koo.project.matcheasy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    /**
     * 글 작성
     */
    public BoardDto registerContent(BoardDto boardDto){

        duplicatedWriter(boardDto);

        log.info("BEFORE CONVERT >>>>>>>>>>>>>>>>>>>>>>>");
        log.info("boardDto title : {}, boardDto content : {}", boardDto.getTitle(), boardDto.getContent());


        BoardContent content = BoardMapper.BOARD_MAPPER.toEntity(boardDto);

        log.info("AFTER CONVERT >>>>>>>>>>>>>>>>>>>>>>>");
        log.info("board title : {}, board content : {}", content.getTitle(), content.getContent());

        boardRepository.save(content);

        return boardDto;
    }


    /**
     * 글 수정
     */



    /**
     * 글 삭제
     */

    /**
     * 중복 작성자 찾기
     * 작성자는 단 한개의 게시글만 등록할 수 있음
     */
    private void duplicatedWriter(BoardDto boardDto) {
        Optional<BoardContent> findDuplicatedWriter = boardRepository.findAll().stream()
                .filter(b -> b.getMember().equals(boardDto.getWriter()))
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
