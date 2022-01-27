package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.team.Dayly;
import koo.project.matcheasy.domain.team.Positionly;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class PositionlyRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Positionly positionly){
        em.persist(positionly);
    }
}
