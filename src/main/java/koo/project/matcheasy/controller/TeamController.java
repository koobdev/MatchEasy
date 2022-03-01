package koo.project.matcheasy.controller;

import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.DaylyDto;
import koo.project.matcheasy.dto.TaskDto;
import koo.project.matcheasy.service.MemberService;
import koo.project.matcheasy.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final MemberService memberService;
    private final TeamService teamService;

    @GetMapping("/{idx}")
    public void searchTeam(@PathVariable("idx") Long idx){
        teamService.searchTeam(idx);
    }


    @PostMapping("/task/register")
    public void registerTask(@RequestBody TaskDto taskDto){
        teamService.registerTask(taskDto);
    }
}
