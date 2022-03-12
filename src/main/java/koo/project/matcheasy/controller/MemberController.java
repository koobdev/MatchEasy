package koo.project.matcheasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberMeDto;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.service.MemberService;
import koo.project.matcheasy.vo.MemberVo;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") MemberDto member){
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public ResponseEntity<MemberDto> save(@Valid @RequestBody MemberDto member){
        MemberDto joinMember = memberService.join(member);
        return ResponseEntity
                .ok()
                .body(joinMember);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<OkResponse> edit(@Valid @RequestBody MemberDto member, @PathVariable("id") Long id) throws JsonProcessingException {
        return memberService.updateMember(id, member);
    }

    @PostMapping("/passwordCheck")
    public ResponseEntity<OkResponse> passwordCheck(
            @Valid @RequestParam("password") String password, @Valid @RequestParam("id") Long id) {
        return memberService.passwordCheck(id, password);
    }

}
