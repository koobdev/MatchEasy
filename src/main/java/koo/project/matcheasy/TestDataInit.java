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
        log.info("init method start >>>>>>>>>>>>>>>>");
        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Spring");
        skills.add("JPA");

        MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .position("Back-End")
                .skills(skills)
                .build();

        log.info("memberDto toString : {}", memberDto.toString());

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);



        log.info("memberEntity toString : {}", memberEntity.toString());
        memberRepository.save(memberEntity);

        log.info("init method end >>>>>>>>>>>>>>>>");
    }
}
