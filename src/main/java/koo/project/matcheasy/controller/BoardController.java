package koo.project.matcheasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board", produces="application/json;charset=utf-8")
public class BoardController {

    private final BoardService boardService;
    private final ObjectMapper objectMapper;

    /**
     * 글 등록
     */
    @PostMapping("/register")
    public ResponseEntity<OkResponse> register(@Valid @RequestBody BoardDto boardDto)
                throws JsonProcessingException {
        return boardService.registerContent(boardDto);
    }

    /**
     * 글 목록 get
     */
    @GetMapping("/list")
    public ResponseEntity<String> boardList() throws JsonProcessingException {
        List<BoardDto> boardLists = boardService.boardList();

        log.info(objectMapper.writeValueAsString(boardLists));

        return ResponseEntity.ok()
                .body(objectMapper.writeValueAsString(boardLists));
    }

    /**
     * 글 열기
     */
    @GetMapping("/{idx}")
    public ResponseEntity<BoardDto> content(@PathVariable("idx") Long idx){
        // 게시글
        BoardDto boardDto = boardService.openContent(idx);

        return ResponseEntity.ok()
                .body(boardDto);
    }

    /**
     * 선택한 포지션에 지원하기
     */
    @PostMapping("/recruit")
    public ResponseEntity<OkResponse> recruitPosition(
            @RequestParam("positionId") Long positionId,
            @RequestParam("recruitMessage") String recruitMessage,
            HttpServletRequest request){

        return boardService.recruit(positionId, recruitMessage, request);
    }




//    @PostMapping("/test/board/update")
//    public ResponseEntity<BoardDto> update(@Valid @RequestBody BoardDto boardDto, HttpServletRequest request){
//        boardService.editContent(boardDto, request);
//
//        return ResponseEntity.ok()
//                .build();
//    }
//
//    @PostMapping("/test/board/update")
//    public ResponseEntity<BoardDto> update(@Valid @RequestBody BoardDto boardDto, HttpServletRequest request) throws Exception {
//        boardService.updateContent(boardDto, request);
//
//        return ResponseEntity.ok().body(boardDto);
//    }
}
