package koo.project.matcheasy.jwt;

import io.jsonwebtoken.*;
import koo.project.matcheasy.dto.LoginDto;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey;
    private long accessTokenValidityInMilliseconds;
    private long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(
            @Value("${security.jwt.accessToken.secret-key}") String secretKey,
            @Value("${security.jwt.accessToken.expire-length}") long accessTokenValidityInMilliseconds,
            @Value("${security.jwt.refreshToken.expire-length}") long refreshTokenValidityInMilliseconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    // access Token 생성
    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime()
                + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // refresh Token 생성
    public String createRefreshToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime()
                + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    //토큰에서 값 추출
    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰 파싱 -> 토큰 생성 로그인 아이디 추출
    public LoginDto parseTokenToLoginId(String token){
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return LoginDto.builder()
                .loginId(body.getSubject())
                .build();
    }
}
