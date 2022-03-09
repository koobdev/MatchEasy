package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.RecruitPosition;
import koo.project.matcheasy.domain.board.RequestPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class RequestPositionRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(RequestPosition position){
        em.persist(position);
    }

    public Optional<RequestPosition> findById(Long id){
        return findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public List<RequestPosition> findAll(){
        return em.createQuery("select r from RequestPosition r", RequestPosition.class)
                .getResultList();
    }
}
