package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Member member){
        em.persist(member);
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    public Optional<Member> findByLoginId(String longinId){
//        List<Member> all = findAll();
//        for (Member m : all){
//            if(m.getLoginId().equals(longinId)){
//                return Optional.of(m);
//            }
//        }
//        return Optional.empty();

        return findAll().stream()
                .filter(m -> m.getLoginId().equals(longinId))
                .findFirst();
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

//    public void clearStore(){
//        store.clear();
//    }
}
