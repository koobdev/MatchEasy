package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.exception.UserDuplicatedException;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     */
    public MemberDto join(MemberDto member){

        validateDuplicateMember(member);

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(member);

        memberRepository.save(memberEntity);
        return member;
    }

    // 중복 회원 검증
    private void validateDuplicateMember(MemberDto member) {

        // 로그인 아이디 중복
        // null이 아니면 중복회원 있음, null이면 중복회원 x
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());

        log.info("service optional check : [{}]", findMember.toString());

        /**
         * Exception TODO
         */
        findMember.ifPresent(m -> {
            try {
                throw new Exception("중복된 회원입니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
