package koo.project.matcheasy.web.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class JwtConfigurer {

    public String makeJwtToken(){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
//                .claim("id", "아이디")
//                .claim("email", "ee@ee.com")
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }
}
