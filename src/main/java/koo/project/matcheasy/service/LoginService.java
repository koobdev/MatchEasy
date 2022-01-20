package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.repository.MemberRepository;
import koo.project.matcheasy.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     *
     * @return null 로그인 실패
     */
    public Member login(String loginId, String password){
//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        Member member = findMemberOptional.get();
//        if(member.getPassword().equals(password)){
//            return member;
//        }else {
//            return null;
//        }

        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }


    public String createToken(MemberDto loginForm) {

        Member findMember = memberRepository.findByLoginId(loginForm.getLoginId())
                .orElseThrow(IllegalArgumentException::new);

        //비밀번호 확인 등의 유효성 검사 진행
        return jwtTokenProvider.createToken(findMember.getLoginId());
    }
}
