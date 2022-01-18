package koo.project.matcheasy;

import koo.project.matcheasy.vo.Member;
import koo.project.matcheasy.repository.MemberRepository;
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
