package koo.project.matcheasy.controller;

import koo.project.matcheasy.service.LoginService;
import koo.project.matcheasy.jwt.TokenResponse;
import koo.project.matcheasy.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") MemberDto form){
        return "login/loginForm";
    }

    /**
     * @return 세션 로그인
     */
//    @PostMapping("/login")
//    public String sessionLogin(@Valid @ModelAttribute MemberDto form, BindingResult bindingResult, HttpServletRequest request,
//                               @RequestParam(defaultValue = "/") String redirectURI){
//
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//
//        if(loginMember == null){
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 로그인 성공 처리
//        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
//        HttpSession session = request.getSession();
//        // 세션에 로그인 회원 정보를 보관
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//
//        return "redirect:" + redirectURI;
//    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }


    // Token Login
    @PostMapping("/token/login")
    public ResponseEntity<TokenResponse> tokenLogin(@Valid @ModelAttribute MemberDto form){

        String token = loginService.createToken(form);

        return ResponseEntity
                .ok()
                .body(new TokenResponse(token, "bearer"));
    }
}
