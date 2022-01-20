package koo.project.matcheasy;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.vo.MemberVo;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);
        memberRepository.save(memberEntity);
    }
}
