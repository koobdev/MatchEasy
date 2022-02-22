package koo.project.matcheasy;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberSkillsDto;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.vo.MemberVo;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        log.info("init method start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<MemberSkillsDto> skills = new ArrayList<>();
        MemberSkillsDto dto1 = MemberSkillsDto.builder()
                .skill("Java")
                .build();
        MemberSkillsDto dto2 = MemberSkillsDto.builder()
                .skill("Spring")
                .build();
        MemberSkillsDto dto3 = MemberSkillsDto.builder()
                .skill("JPA")
                .build();
        skills.add(dto1);
        skills.add(dto2);
        skills.add(dto3);


        MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .position("Back-End")
                .memberSkills(skills)
                .build();

        MemberDto memberDto2 = MemberDto.builder()
                .loginId("test2")
                .password("test!")
                .name("테스트2")
                .age(22)
                .email("bb@bb.com")
                .position("Front-End")
                .memberSkills(skills)
                .build();

        log.info("memberDto toString : {}", memberDto.toString());
        log.info("memberDto2 toString : {}", memberDto2.toString());

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);
        Member memberEntity2 = MemberMapper.MEMBER_MAPPER.toEntity(memberDto2);


        log.info("memberEntity toString : {}", memberEntity.toString());
        memberEntity.getMemberSkills().stream()
                .forEach(s -> {
                    log.info("Detail >>> getSkill : {}", s.getSkill());
                    s.addMember(memberEntity);
                });
        log.info("memberEntity2 toString : {}", memberEntity2.toString());




        memberRepository.save(memberEntity);
        memberRepository.save(memberEntity2);

        log.info("init method end >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
