package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.team.Team;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.repository.MemberRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
//@Rollback(value = false)
@SpringBootTest
class MemberServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("MapStruct Dto<->Entity 변환 테스트")
    void MapStructTest(){
        final MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        final Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);

        assertThat(memberDto.getLoginId()).isEqualTo(memberEntity.getLoginId());
        assertThat(memberDto.getPassword()).isEqualTo(memberEntity.getPassword());
        assertThat(memberDto.getName()).isEqualTo(memberEntity.getName());
        assertThat(memberDto.getAge()).isEqualTo(memberEntity.getAge());
        assertThat(memberDto.getEmail()).isEqualTo(memberEntity.getEmail());
    }


    @Test
    @DisplayName("회원가입")
    void joinTest(){

        final MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(memberDto);
        memberRepository.save(memberEntity);
        Optional<Member> findMember = memberRepository.findByLoginId("test");


        assertThat(memberEntity.getLoginId()).isEqualTo(findMember.get().getLoginId());
    }

    @Test
    public void joinTest2(){
        // given
        final MemberDto memberDto = MemberDto.builder()
                .loginId("test")
                .password("test!")
                .name("테스트")
                .age(20)
                .email("aa@aa.com")
                .build();

        // when
        memberService.join(memberDto);

        //then
        Optional<Member> findMember = memberRepository.findByLoginId("test");
        assertThat(memberDto.getLoginId()).isEqualTo(findMember.get().getLoginId());
    }

    @Test
    @DisplayName("")
    void teamAddTest(){
        // given
        Optional<Member> optionalMember = memberRepository.findByLoginId("test");
        Member member = optionalMember.get();
        Team team = new Team();
        team.addMember(member);

        // when
        // then
        assertThat(member.getTeam()).isEqualTo(team.getId());
    }


    @Test
    @DisplayName("")
    void findMemberSkills(){
        // given
        Member findMember = em.find(Member.class, 1L);
        log.info("findMember >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("memberName : {}, memberLoginId : {}", findMember.getName(), findMember.getLoginId());

        // when, then
//        List<String> getSkills = findMember.getSkills();
//        log.info("getSkiils >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//        for (String s : getSkills) {
//            log.info("skill : {} / ", s);
//        }

    }
}