package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Chat chat){
        em.persist(chat);
    }

    public Chat findById(Long id){
        return em.find(Chat.class, id);
    }
}
