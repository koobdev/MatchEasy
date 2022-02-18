package koo.project.matcheasy.jwt;

import io.jsonwebtoken.*;
import koo.project.matcheasy.dto.LoginDto;
import koo.project.matcheasy.dto.MemberMeDto;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.accessToken.secret-key}")
    private String secretKey;

    private long accessTokenValidityInMilliseconds = 1000L * 60 * 30; // 30분;
    private long refreshTokenValidityInMilliseconds = 1000L * 60 * 60 * 24 * 7; // 7일;

    public String getSecretKey(){
        return Base64.getEncoder().encodeToString(this.secretKey.getBytes());
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
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();
    }


    // refresh Token 생성
    public String createRefreshToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime()
                + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();
    }


    //토큰에서 값 추출
    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token);
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
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody();

        return LoginDto.builder()
                .loginId(body.getSubject())
                .build();
    }
}
