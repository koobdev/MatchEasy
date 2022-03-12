package koo.project.matcheasy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.*;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.MemberContext;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.mapper.MemberSkillsMapper;
import koo.project.matcheasy.mapper.RecruitPositionMapper;
import koo.project.matcheasy.repository.MemberRepository;
import koo.project.matcheasy.repository.MemberSkillsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static koo.project.matcheasy.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberSkillsRepository memberSkillsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authExtractor;
    private final ObjectMapper objectMapper;

    /**
     * 회원 가입
     */
    public MemberDto join(MemberDto member){

        validateDuplicateMember(member);

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(member);

        // TODO : memberEntity에 getMemberSKills가 null로 이미 저장되어서 넘어와서 중복 저장됨
        for (MemberSkillsDto memberSkillsDto : member.getMemberSkills()) {
            MemberSkills memberSkillsEntity = MemberSkillsMapper.MEMBER_SKILLS_MAPPER.toEntity(memberSkillsDto);
            memberSkillsEntity.addMember(memberEntity);
        }

        memberRepository.save(memberEntity);
        return MemberMapper.MEMBER_MAPPER.toDto(memberEntity);
    }

    /**
     * 회원 정보수정
     */
    public ResponseEntity<OkResponse> updateMember(Long id, MemberDto member) throws JsonProcessingException {

        List<MemberSkills> skills = new ArrayList<>();
        for (MemberSkillsDto memberSkill : member.getMemberSkills()) {
            MemberSkills memberSkills = MemberSkillsMapper.MEMBER_SKILLS_MAPPER.toEntity(memberSkill);
            skills.add(memberSkills);
        }

        Member findMember = memberRepository.findById(id);
        if(!Objects.equals(member.getPassword(), "NP")){
            findMember.updateMember(member.getPassword(), member.getEmail(), member.getPosition(), skills);
        }else {
            findMember.updateMemberNotPw(member.getEmail(), member.getPosition(), skills);
        }

        return OkResponse.toResponse(objectMapper.writeValueAsString(findMember),"회원정보를 수정하였습니다.");
    }

    /**
     * 회원 비밀번호 확인
     */
    public ResponseEntity<OkResponse> passwordCheck(Long id, String password) {

        validateMemberPassword(id,password);
        return OkResponse.toResponse("ok","비밀번호가 일치합니다.");
    }




    /**
     * 로그인 회원 정보 추출
     */
    public MemberMeDto me(HttpServletRequest request){

        String token = authExtractor.extract(request, "Bearer");
        LoginDto loginDto = jwtTokenProvider.parseTokenToLoginId(token);
        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseGet(() -> {
                    throw new CustomException(MEMBER_NOT_FOUND);
                });

        MemberMeDto build = MemberMeDto.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .age(member.getAge())
                .email(member.getEmail())
                .position(member.getPosition())
                .memberSkills(member.getMemberSkills())
                .teamId(member.getTeam() == null ? 0 : member.getTeam().getId())
                .build();

        return build;
    }


    // 중복 회원 검증
    private void validateDuplicateMember(MemberDto member) {

        // 로그인 아이디 중복
        // null이 아니면 중복회원 있음, null이면 중복회원 x
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());

        log.info("service optional check : [{}]", findMember.toString());

        findMember.ifPresent(m -> {
            throw new CustomException(MEMBER_DUPLICATED);
        });
    }

    // 회원 비밀번호 검증
    private void validateMemberPassword(Long id, String password) {
        Member findMember = memberRepository.findById(id);
        if(!findMember.getPassword().equals(password)){
            throw new CustomException(MEMBER_PASSWORD_MISMATCH);
        }
    }
}
