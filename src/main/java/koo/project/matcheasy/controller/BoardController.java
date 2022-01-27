package koo.project.matcheasy.controller;

import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/test/board")
    public ResponseEntity<BoardDto> register(@Valid @RequestBody BoardDto boardDto){
        BoardDto response = boardService.registerContent(boardDto);

        return ResponseEntity.ok().body(response);
    }
}
