package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.member.MemberSkills;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class MemberSkillsRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(MemberSkills skills){
        em.persist(skills);
    }
}
