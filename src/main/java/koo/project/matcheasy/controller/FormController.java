package koo.project.matcheasy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/form")
public class FormController {

    @GetMapping("/chatRoom")
    public String chatRoomForm(){
        return "/form/chatRoom";
    }

    /**
     * 글 목록 폼
     */
    @GetMapping("/contentList")
    public String boardListForm(){
        return "/board/list";
    }

    /**
     * 글 열기 폼
     */
    @GetMapping("/contentDetail")
    public String boardDetailForm(){
        return "/form/contentDetail";
    }
}
