package koo.project.matcheasy.controller;

import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/test/board/register")
    public ResponseEntity<BoardDto> register(@Valid @RequestBody BoardDto boardDto){
        BoardDto response = boardService.registerContent(boardDto);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/board/{idx}")
    public void content(@PathVariable("idx") int idx){

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
