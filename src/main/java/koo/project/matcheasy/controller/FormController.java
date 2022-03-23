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
        return "chat/chatRoom";
    }

    /**
     * 회원가입 폼
     */
    @GetMapping("/addMember")
    public String addMemberForm(){
        return "members/addMember";
    }

    /**
     * 글 목록 폼
     */
    @GetMapping("/contentList")
    public String boardListForm(){
        return "board/list";
    }

    /**
     * 글 열기 폼
     */
    @GetMapping("/contentDetail")
    public String boardDetailForm(){
        return "board/contentDetail";
    }

    /**
     * 게시글 관리페이지 폼
     */
    @GetMapping("/manageBoardContent")
    public String manageBoardContentForm(){
        return "myPage/manageBoardContent";
    }

    /**
     * 지원자 관리 폼
     */
    @GetMapping("/manageRecruit")
    public String manageRecruitForm(){
        return "myPage/manageRecruit";
    }

    /**
     * 나의 지원 관리 폼
     */
    @GetMapping("/manageMyRecruit")
    public String manageMyRecruitForm(){
        return "myPage/manageMyRecruit";
    }

    /**
     * 회원정보 수정 폼
     */
    @GetMapping("/editMember")
    public String editMemberForm(){
        return "members/editMember";
    }

    /**
     * 나의 지원 관리 폼
     */
    @GetMapping("/manageTeam")
    public String manageTeamForm(){
        return "team/manageTeam";
    }
}
