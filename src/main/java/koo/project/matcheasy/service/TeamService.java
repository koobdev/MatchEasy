package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberMeDto;
import koo.project.matcheasy.dto.OkResponse;
import koo.project.matcheasy.dto.TeamDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.mapper.TeamMapper;
import koo.project.matcheasy.mapper.TeamMemberMapper;
import koo.project.matcheasy.repository.BoardRepository;
import koo.project.matcheasy.repository.MemberRepository;
import koo.project.matcheasy.repository.RecruitPositionRepository;
import koo.project.matcheasy.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static koo.project.matcheasy.exception.ErrorCode.CONTENT_NOT_FOUND;
import static koo.project.matcheasy.exception.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final RecruitPositionRepository recruitPositionRepository;
    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 팀 생성하기
     */
    public ResponseEntity<OkResponse> createTeam(TeamDto teamDto, HttpServletRequest request){
        String token = authExtractor.extract(request, "Bearer");
        String loginId = jwtTokenProvider.getSubject(token);


        validateDuplicateTeam(teamDto);

        teamDto.setMembers(getAcceptedList(loginId));
        for (MemberDto member : teamDto.getMembers()) {
            log.info("teamDto ::::: {}", member.getLoginId());
        }

        // 변환 확인
        // addTeam에러
        Team team = TeamMapper.TEAM_MAPPER.toEntity(teamDto);
        for (Member member : team.getMembers()) {
            member.addTeam(team);
        }

//        team.addMember(memberEntity);

//        teamRepository.save(team);
//        getAcceptedListAndSaveTeam(loginId, team);


//        log.info("AFTER::::::::::: {} ", team.toString());

//        teamRepository.save(team);
//        memberRepository.findByLoginId(loginId)
//                .ifPresent(member -> member.addTeam(team));

        teamRepository.save(team);

        return OkResponse.toResponse("ok","팀 생성을 완료했습니다.");
    }

    private void validateDuplicateTeam(TeamDto teamDto) {

    }


    /**
     * 수락 처리된 지원자 리스트
     */
    public List<MemberDto> getAcceptedList(String loginId){
        List<MemberDto> memberDtos = new ArrayList<>();

        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseGet(() -> {
                    throw new CustomException(MEMBER_NOT_FOUND);
                });

        boardRepository.findByWriterId(findMember.getId())
                .ifPresentOrElse(content -> {
                    for (RecruitPosition position : content.getPositions()) {
                        if(position.getBoardContent().getId().equals(content.getId())
                                && position.getStatus() == 2){
                            MemberDto memberDto = MemberMapper.MEMBER_MAPPER.toDto(position.getRecruitMember());
                            memberDtos.add(memberDto);
                        }
                    }
                }, () -> {
                    throw new CustomException(CONTENT_NOT_FOUND);
                });

        // 글 작성자도 팀에 추가
//        findMember.addTeam(team);

        return memberDtos;
    }
}
