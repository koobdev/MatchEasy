package koo.project.matcheasy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.board.RequestPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.team.Task;
import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.domain.team.TeamPosition;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.dto.TaskDto;
import koo.project.matcheasy.dto.TeamDto;
import koo.project.matcheasy.dto.TeamSearchDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.TaskMapper;
import koo.project.matcheasy.mapper.TeamMapper;
import koo.project.matcheasy.mapper.TeamSearchMapper;
import koo.project.matcheasy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static koo.project.matcheasy.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final RecruitPositionRepository recruitPositionRepository;
    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;



    /**
     * 팀 생성하기
     *  1. 수락된 지원자들로 구성된 팀 생성하기 && 팀장 id넣기
     *  2. 게시물 상태를 모집 완료(status : 1)로 변경 하기
     *  3. 팀원 포지션 테이블 생성 및 데이터 삽입
     */
    public ResponseEntity<OkResponse> createTeam(TeamDto teamDto, HttpServletRequest request){

        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        // 1.
        Team team = TeamMapper.TEAM_MAPPER.toEntity(teamDto);
        Member findMe = memberRepository.findByLoginId(loginId)
                .orElseGet(() -> {
                    throw new CustomException(MEMBER_NOT_FOUND);
                });

        Map<String, Object> acceptedList = getAcceptedList(findMe);
        convertObjectToMemberList(acceptedList.get("members"))
                .forEach(member -> member.addTeam(team));

        team.updateLeaderId(findMe.getId());

        // 2.
        BoardContent findContent = (BoardContent) acceptedList.get("findContent");
        findContent.updateStatus(1);


        teamRepository.save(team);

        // 3.
        convertObjectToTeamPositionList(acceptedList.get("positions"))
                .forEach(position -> {
                    log.info("TeamPositions positions :::: {} ", position);
                    TeamPosition teamPosition = TeamPosition.builder()
                            .position(position)
                            .build();
                    log.info("TeamPositions toString :::: {} ", teamPosition.toString());
                    teamPosition.addTeam(team);
                });

        return OkResponse.toResponse("ok","팀 생성을 완료했습니다.");
    }



    /**
     * 수락 처리된 지원자 리스트
     */
    public Map<String, Object> getAcceptedList(Member findMe){
        Map<String, Object> returnMap = new HashMap<>();
        List<Member> members = new ArrayList<>();
        List<String> positions = new ArrayList<>();

        boardRepository.findByWriterId(findMe.getId())
                .ifPresentOrElse(content -> {
                    for (RecruitPosition position : content.getPositions()) {
                        if(position.getBoardContent().getId().equals(content.getId())
                                && position.getStatus() == 1){

                            // RequestPosition에서 수락된 인원을 찾아 members에 넣는다.
                            for (RequestPosition requestPosition : position.getRequestPosition()) {
                                if(requestPosition.getStatus() == 1){
                                    Long id = requestPosition.getRecruitMember().getId();
                                    Member findRecruitMember = memberRepository.findById(id);
                                    members.add(findRecruitMember);
                                }
                            }
                            // positions에 글 작성시 구인했던 포지션의 이름을 넣는다.
                            positions.add(position.getPosition());
                        }
                    }
                    returnMap.put("findContent", content);
                }, () -> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });

        // 글 작성자도 팀에 추가
        members.add(findMe);
        // 글 작성자도 팀 포지션에 추가
        positions.add(findMe.getPosition());

        returnMap.put("members", members);
        returnMap.put("positions", positions);
        return returnMap;
    }


    /**
     * 일일 일정 추가
     */
    public ResponseEntity<OkResponse> registerTask(TaskDto taskDto) throws JsonProcessingException {
        Team findTeam = teamRepository.findById(taskDto.getTeamId());
        Task taskEntity = TaskMapper.TASK_MAPPER.toEntity(taskDto);

        taskEntity.addTeam(findTeam);
        taskRepository.save(taskEntity);

        return OkResponse.toResponse(objectMapper.writeValueAsString(taskDto),"일정을 추가하였습니다.");
    }

    /**
     * 일정 가져오기
     */
    public List<TaskDto> getAllTask(Long id){
        List<TaskDto> returnList = new ArrayList<>();
        taskRepository.findByTeamId(id)
                .forEach(task -> {
                    TaskDto dto = TaskMapper.TASK_MAPPER.toDto(task);
                    returnList.add(dto);
                });

        return returnList;
    }

    /**
     * 일정 수정하기 (상태 업데이트)
     */
    public ResponseEntity<OkResponse> updateTask(Long id, HttpServletRequest request) throws JsonProcessingException {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        checkTeamLeader(loginId, id);

        Task findTask = taskRepository.findById(id);
        findTask.updateStatus(1);

        return OkResponse.toResponse(objectMapper.writeValueAsString(findTask),"선택한 일정을 완료처리하였습니다.");
    }

    /**
     * 일정 삭제하기
     */
    public ResponseEntity<OkResponse> deleteTask(Long id, HttpServletRequest request) throws JsonProcessingException {
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);

        checkTeamLeader(loginId, id);

        Task findTask = taskRepository.findById(id);
        taskRepository.delete(findTask);

        return OkResponse.toResponse(objectMapper.writeValueAsString(findTask),"일정을 삭제하였습니다.");
    }

    /**
     * 팀 검색 (리스트)
     */
    public ResponseEntity<OkResponse> searchTeam(Long idx) throws JsonProcessingException {
        Team findTeam = teamRepository.findById(idx);
        TeamSearchDto teamSearchDto = TeamSearchMapper.TEAM_SEARCH_MAPPER.toDto(findTeam);

        return OkResponse.toResponse(objectMapper.writeValueAsString(teamSearchDto),"팀 데이터");
    }


    // 팀장 체크
    private void checkTeamLeader(String loginId, Long id) {
        Task findTask = taskRepository.findById(id);
        memberRepository.findByLoginId(loginId)
                .ifPresentOrElse(member -> {
                    if(!member.getId().equals(findTask.getTeam().getLeaderId())){
                        throw new CustomException(TEAM_LEADER_MISMATCH);
                    }
                }, ()->{
                    throw new CustomException(MEMBER_NOT_FOUND);
                });
    }


    // object to list
    public List<Member> convertObjectToMemberList(Object obj) {
        List<Member> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Member)obj);
        }else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<Member>)obj);
        }
        return list;
    }

    public List<String> convertObjectToTeamPositionList(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((String)obj);
        }else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<String>)obj);
        }
        return list;
    }



}
