package koo.project.matcheasy.service;

import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.LoginDto;
import koo.project.matcheasy.dto.MemberDto;
import koo.project.matcheasy.dto.MemberMeDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.mapper.MemberMapper;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static koo.project.matcheasy.exception.ErrorCode.MEMBER_DUPLICATED;
import static koo.project.matcheasy.exception.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authExtractor;

    /**
     * 회원 가입
     */
    public MemberDto join(MemberDto member){

        validateDuplicateMember(member);

        Member memberEntity = MemberMapper.MEMBER_MAPPER.toEntity(member);

        memberRepository.save(memberEntity);
        return member;
    }


    /**
     * 로그인 회원 정보 추출
     */
    public MemberMeDto me(HttpServletRequest request){

        String token = authExtractor.extract(request, "Bearer");
        LoginDto loginDto = jwtTokenProvider.parseTokenToLoginId(token);
        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseGet(() -> {
                    throw new CustomException(MEMBER_NOT_FOUND);
                });

        return MemberMeDto.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .age(member.getAge())
                .email(member.getEmail())
                .build();
    }


    // 중복 회원 검증
    private void validateDuplicateMember(MemberDto member) {

        // 로그인 아이디 중복
        // null이 아니면 중복회원 있음, null이면 중복회원 x
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());

        log.info("service optional check : [{}]", findMember.toString());

        findMember.ifPresent(m -> {
            throw new CustomException(MEMBER_DUPLICATED);
        });
    }
}
