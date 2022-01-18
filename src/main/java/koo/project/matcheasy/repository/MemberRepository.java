package koo.project.matcheasy.repository;

import koo.project.matcheasy.domain.member.MemberEntity;
import koo.project.matcheasy.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private final EntityManager em;

    public void save(Member member){
        log.info("save: member={}", member);
        em.persist(member);
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    public Optional<MemberEntity> findByLoginId(String longinId){
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

    public List<MemberEntity> findAll(){
        return em.createQuery("select m from MemberEntity m", MemberEntity.class)
                .getResultList();
    }

//    public void clearStore(){
//        store.clear();
//    }
}
