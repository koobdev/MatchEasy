package koo.project.matcheasy.interceptor;

import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static koo.project.matcheasy.exception.ErrorCode.INVALID_AUTH_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">>> interceptor.preHandle 호출");
        String token = authExtractor.extract(request, "Bearer");

        // 요청 헤더에 토큰이 존재 하지 않거나, 유효성 검사을 실패 했을 때 Exception
        if (token.isEmpty() || !jwtTokenProvider.validateToken(token)) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        String name = jwtTokenProvider.getSubject(token);
        System.out.println(">>> access token : " + name);
        request.setAttribute("name", name);
        return true;
    }
}
