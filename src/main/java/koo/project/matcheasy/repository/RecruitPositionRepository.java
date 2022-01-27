package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.RecruitPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class RecruitPositionRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(RecruitPosition position){
        em.persist(position);
    }
}
