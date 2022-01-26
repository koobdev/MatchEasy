package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.BoardContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(BoardContent board){
        em.persist(board);
    }

    public BoardContent findById(Long id){
        return em.find(BoardContent.class, id);
    }

    public List<BoardContent> findAll(){
        return em.createQuery("select b from BoardContent b order by b.regdate asc", BoardContent.class)
                .getResultList();
    }

    public List<BoardContent> findAllByPage(int start, int end){
        return em.createQuery("select b from BoardContent b order by b.regdate asc", BoardContent.class)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }
}