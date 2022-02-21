package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.RecruitPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class RecruitPositionRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(RecruitPosition position){
        em.persist(position);
    }

    public List<RecruitPosition> findAll(){
        return em.createQuery("select r from RecruitPosition r", RecruitPosition.class)
                .getResultList();
    }

    public Optional<RecruitPosition> findById(Long id){
        return findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

//    public RecruitPosition findById(Long id){
//        return em.find(RecruitPosition.class, id);
//    }
}
