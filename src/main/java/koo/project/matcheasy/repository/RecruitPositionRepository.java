package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.RecruitPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
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

    public List<RecruitPosition> findByContentId(Long id){

//        List<RecruitPosition> collect = findAll().stream()
//                .filter(r -> r.getBoardContent().getId().equals(id))
//                .collect(Collectors.toList());
//
//        for (RecruitPosition position : collect) {
//            log.info("findByContentId OF positionId : {}", position.getId());
//        }
//        return collect;

        return em.createQuery("select r from RecruitPosition r where r.content=:id", RecruitPosition.class)
                .setParameter("id", id)
                .getResultList();
    }

//    public RecruitPosition findById(Long id){
//        return em.find(RecruitPosition.class, id);
//    }
}
