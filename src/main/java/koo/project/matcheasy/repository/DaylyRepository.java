package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.team.Dayly;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class DaylyRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Dayly dayly){
        em.persist(dayly);
    }
}
