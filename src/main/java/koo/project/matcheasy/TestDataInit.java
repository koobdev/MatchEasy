package koo.project.matcheasy;

import koo.project.matcheasy.controller.MemberController;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberSkills;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberSkillsDto;
import koo.project.matcheasy.mapper.MemberContext;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.service.MemberService;
import koo.project.matcheasy.vo.MemberVo;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberContext memberContext;
    private final MemberController memberController;

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
//
//
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

//        List<Map<String, Object>> skill = new ArrayList<>();
//        Map<String, Object> skill1 = new HashMap<>();
//        skill1.put("skill", "Java");
//        Map<String, Object> skill2 = new HashMap<>();
//        skill2.put("skill", "Spring");
//        Map<String, Object> skill3 = new HashMap<>();
//        skill3.put("skill", "JPA");
//        skill.add(skill1);
//        skill.add(skill2);
//        skill.add(skill3);
//
//        Map<String, Object> memberDto = new HashMap<>();
//        memberDto.put("loginId", "test");
//        memberDto.put("password", "test!");
//        memberDto.put("name", "테스트");
//        memberDto.put("age", 20);
//        memberDto.put("email", "aa@aa.com");
//        memberDto.put("position", "Back-End");
//        memberDto.put("skills", skill);

        log.info("memberDto toString : {}", memberDto.toString());
//        log.info("memberDto2 toString : {}", memberDto2.toString());


        memberService.join(memberDto);
        memberService.join(memberDto2);

//        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto, memberContext);
//        Member memberEntity2 = MemberMapper.MEMBER_MAPPER.toEntity(memberDto2, memberContext);


//        log.info("memberEntity toString : {}", memberEntity.toString());
//        memberEntity.getMemberSkills().stream()
//                .forEach(s -> {
//                    log.info("Detail >>> getSkill : {}", s.getSkill());
//                    s.addMember(memberEntity);
//                });
//        log.info("memberEntity2 toString : {}", memberEntity2.toString());




//        memberRepository.save(memberEntity);
//        memberRepository.save(memberEntity2);

        log.info("init method end >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
