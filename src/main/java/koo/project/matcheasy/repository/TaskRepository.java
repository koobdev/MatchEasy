package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.team.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class TaskRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Task task){
        em.persist(task);
    }

    public List<Task> findAll(){
        return em.createQuery("select t from Task t", Task.class)
                .getResultList();
    }

    public List<Task> findByTeamId(Long id){
        return em.createQuery("select t from Task t where t.team=:id", Task.class)
                .setParameter("id", id)
                .getResultList();
    }
}
