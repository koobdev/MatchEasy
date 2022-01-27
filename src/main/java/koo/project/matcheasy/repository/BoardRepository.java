package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.board.BoardContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public void update(BoardContent board){
        BoardContent byId = findById(board.getId());
        // TODO
        // Service단에서 toEntity로 넘어왔기 때문에 update를 위해 데이터를 갈아끼우기가 애매함
        // 다시 dto변환을 해야할까? Builder를 이용해야할까?
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
}
