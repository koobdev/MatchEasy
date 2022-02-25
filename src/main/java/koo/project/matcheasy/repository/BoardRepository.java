package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.BoardContent;
import koo.project.matcheasy.mapper.BoardContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(BoardContent board){
        em.persist(board);
    }

    @Transactional
    public void delete(BoardContent board){
        em.remove(board);
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

    public Optional<BoardContent> findByWriterId(Long id){
//        return Optional.of(
//                em.createQuery("select b from BoardContent b where b.writerId=:id", BoardContent.class)
//                .setParameter("id", id)
//                .getSingleResult()
//        );

        return findAll().stream()
                .filter(c -> c.getWriterId().equals(id))
                .findAny();
    }
}
