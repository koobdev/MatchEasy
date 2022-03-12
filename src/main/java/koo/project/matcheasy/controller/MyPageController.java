package koo.project.matcheasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.dto.BoardDto;
import koo.project.matcheasy.dto.MemberMeDto;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.dto.TeamDto;
import koo.project.matcheasy.service.BoardService;
import koo.project.matcheasy.service.MemberService;
import koo.project.matcheasy.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyPageController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final TeamService teamService;

    /**
     * 개인 정보
     */
    @GetMapping("/me")
    public ResponseEntity<MemberMeDto> me(HttpServletRequest request){

        log.info("token >>>>>>>> {}", request.getHeader("Authorization"));
        MemberMeDto memberMeDto = memberService.me(request);

        log.info("memberMeDto ::::: {} ", memberMeDto.toString());
        return ResponseEntity
                .ok()
                .body(memberMeDto);
    }


    /**
     * 글 목록 get
     */
    @GetMapping("/boardContent")
    public ResponseEntity<BoardDto> myContent(@RequestParam("userId") Long userId){
        // 게시글
        BoardDto boardDto = boardService.openMyContent(userId);

        return ResponseEntity.ok()
                .body(boardDto);
    }


    /**
     * 지원자 목록
     * 내가 작성한 글과 포지션 지원자를 join해서 물고옴
     */
    @GetMapping("/recruitList")
    public ResponseEntity<OkResponse> recruitList(HttpServletRequest request){

        List<RecruitPosition> positions = boardService.recruitList(request);
        return OkResponse.toListResponse(Collections.singletonList(positions), "지원자 목록");
    }


    /**
     * 요청 수락 OR 거절
     */
    @PostMapping("/acceptOrReject")
    public ResponseEntity<OkResponse> acceptOrReject(
            @RequestParam("idx") Long idx,
            @RequestParam("status") int status,
            @RequestParam("message") String message){

        return boardService.acceptOrReject(idx, status, message);
    }

}
