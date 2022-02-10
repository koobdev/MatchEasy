package koo.project.matcheasy.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
}
