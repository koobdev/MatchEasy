package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.chat.Chat;
import koo.project.matcheasy.domain.chat.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ChatRoomRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(ChatRoom chatRoom){
        em.persist(chatRoom);
    }

    @Transactional
    public void delete(ChatRoom chatRoom){
        em.remove(chatRoom);
    }

    public List<ChatRoom> findAllRooms(){
        return em.createQuery("select r from ChatRoom r order by r.regdate desc", ChatRoom.class)
                .getResultList();
    }

    public ChatRoom findByRoomId(Long id){
        return em.find(ChatRoom.class, id);
    }

    public Optional<ChatRoom> findByWriterId(Long id){
        return findAllRooms().stream()
                .filter(r -> r.getWriterId().equals(id))
                .findFirst();
    }
}
