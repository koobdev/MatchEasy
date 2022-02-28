package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamRepositoryTest {

    Logger log = (Logger) LoggerFactory.getLogger(TeamRepositoryTest.class);

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("")
    void teamSaveTest(){
        // given
        Optional<Member> findMember = memberRepository.findByLoginId("test");
        Member member = findMember.get();
        Team team = new Team();
//        team.setName("testTeam");
//        team.addMember(member);

        // when
        teamRepository.findByMember(member)
                .ifPresentOrElse(null ,() -> {
                    teamRepository.save(team);
                });

        // then
        Optional<Member> saveMember = teamRepository.findAll().get(0).getMembers().stream()
                .filter(m -> m.getId().equals(member.getId()))
                .findAny();

        log.info("MemberLoginId={}, MemberName={}", saveMember.get().getLoginId(), saveMember.get().getName());

        assertThat(saveMember.get().getName()).isEqualTo(member.getName());
    }
}