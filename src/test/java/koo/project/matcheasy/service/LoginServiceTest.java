package koo.project.matcheasy.service;

import koo.project.matcheasy.dto.LoginDto;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import koo.project.matcheasy.jwt.TokenResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    LoginService loginService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("RefreshToken 로그인 테스트")
    void refreshTokenLoginTest(){
        // given

        LoginDto loginMember = LoginDto.builder()
                .loginId("test")
                .password("test!")
                .build();
        TokenResponse accessToken = loginService.tokenIssued(loginMember);

        TokenResponse tokenResponse = new TokenResponse(
                "a1234", "r1234", "Bearer"
        );

        // when
        TokenResponse refreshToken = loginService.tokenReIssued(tokenResponse);
        LoginDto findMem = jwtTokenProvider.parseTokenToLoginId(refreshToken.getRefreshToken());


        // then
        assertThat(loginMember.getLoginId()).isEqualTo(findMem.getLoginId());
        assertThat(loginMember.getPassword()).isEqualTo(findMem.getPassword());
    }
}