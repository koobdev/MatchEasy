package koo.project.matcheasy.controller;

import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.DaylyDto;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.dto.TaskDto;
import koo.project.matcheasy.dto.TeamDto;
import koo.project.matcheasy.service.MemberService;
import koo.project.matcheasy.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    /**
     * 팀 구성하기
     */
    @PostMapping("/createTeam")
    public ResponseEntity<OkResponse> createTeam(@Valid @RequestBody TeamDto teamDto, HttpServletRequest request){

        log.info("Controller TeamDTO : {} ", teamDto.toString());
        return teamService.createTeam(teamDto, request);
    }
}
