package koo.project.matcheasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final MemberService memberService;
    private final TeamService teamService;
    private final ObjectMapper objectMapper;

    /**
     * 팀 구성하기
     */
    @PostMapping("/createTeam")
    public ResponseEntity<OkResponse> createTeam(@Valid @RequestBody TeamDto teamDto, HttpServletRequest request){

        log.info("Controller TeamDTO : {} ", teamDto.toString());
        return teamService.createTeam(teamDto, request);
    }

    /**
     * 팀 검색
     */
    @GetMapping("/{idx}")
    public ResponseEntity<OkResponse> searchTeam(@PathVariable("idx") Long idx) throws JsonProcessingException {
        return teamService.searchTeam(idx);
    }

    /**
     * 일정 목록 가져오기
     */
    @PostMapping("/task/list")
    public ResponseEntity<String> taskList(@RequestParam("teamId") Long id) throws JsonProcessingException {
        List<TaskDto> taskList = teamService.getAllTask(id);

        return ResponseEntity.ok()
                .body(objectMapper.writeValueAsString(taskList));
    }

    /**
     * 일정 등록하기
     */
    @PostMapping("/task/register")
    public ResponseEntity<OkResponse> registerTask(@RequestBody TaskDto taskDto) throws JsonProcessingException {
        return teamService.registerTask(taskDto);
    }


}
