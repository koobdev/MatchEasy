package koo.project.matcheasy;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스트");

        memberRepository.save(member);
    }
}
