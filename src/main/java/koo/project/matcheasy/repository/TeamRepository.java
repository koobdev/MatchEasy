package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.domain.team.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeamRepository {

    @PersistenceContext // 생략가능
    private final EntityManager em;

    // crud

    @Transactional
    public void save(Team team){
        em.persist(team);
    }


    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    public Team findById(Team team){
        return em.find(Team.class, team.getId());
    }


    public Optional<Team> findByMember(Member member){
        return findAll().stream()
                .filter(t -> t.getId().equals(member.getTeam().getId()))
                .findFirst();
    }
}
