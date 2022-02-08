package koo.project.matcheasy.controller;

import koo.project.matcheasy.vo.MemberVo;
import koo.project.matcheasy.repository.MemberRepository;
import koo.project.matcheasy.common.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(){
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberVo member, Model model){

        /*
        // 세션을 생성할 의도가 없기 때문에 false로 받아줌
        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        */



        // 세션에 회원 데이터가 없으면
        if(member == null){
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", member);
        return "loginHome";
    }
}
