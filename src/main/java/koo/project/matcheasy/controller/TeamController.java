package koo.project.matcheasy.controller;

import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final MemberService memberService;


//    @PostMapping("/test/team/register")
//    public ResponseEntity<Team> teamRegister(){
//
//    }
}
