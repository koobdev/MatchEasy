package koo.project.matcheasy.service;

import antlr.TokenStreamRecognitionException;
import koo.project.matcheasy.domain.member.Member;
import koo.project.matcheasy.dto.LoginDto;
import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.jwt.TokenResponse;
import koo.project.matcheasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static koo.project.matcheasy.exception.ErrorCode.INVALID_AUTH_TOKEN;
import static koo.project.matcheasy.exception.ErrorCode.REFRESH_TOKEN_EXPIRED;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final AuthorizationExtractor authExtractor;
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


    /**
     * 토큰 발급 (AccessToken, RefreshToken)
     */
    public TokenResponse tokenIssued(LoginDto loginDto) {

        String accessToken = jwtTokenProvider.createToken(loginDto.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        log.info("refresh Token ::::::::: {}", refreshToken);

        // refreshToken DB 저장 (추후 Redis 적용)
        Member member = memberRepository.findByLoginId(loginDto.getLoginId()).get();
        member.addRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken, "Bearer");
    }


    /**
     * 토큰 재발급
     * 1. AccessToken 만료시 ::: RefreshToken 비교 후, AccessToken, RefreshToken 재발급
     * 2. RefreshToken 만료시 :::  RefreshToken 비교 시, Exception 발생 -> 재로그인
     */
    public TokenResponse tokenReIssued(TokenResponse response){
        LoginDto loginDto = jwtTokenProvider.parseTokenToLoginId(response.getAccessToken());

        memberRepository.findByLoginId(loginDto.getLoginId())
                .filter(member -> member.getRefreshToken().equals(response.getRefreshToken()))
                .ifPresentOrElse((member) -> {
                }, () -> {
                    throw new CustomException(REFRESH_TOKEN_EXPIRED);
                });

        return tokenIssued(loginDto);
    }
}
