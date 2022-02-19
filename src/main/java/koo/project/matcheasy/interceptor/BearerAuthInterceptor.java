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
import static koo.project.matcheasy.exception.ErrorCode.NOT_EXIST_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info(">>> interceptor.preHandle 호출");
        log.info(">>>>>>>>> getUrl() :  {}", request.getRequestURI());
        String token = authExtractor.extract(request, "Bearer");
        log.info(">>>>>>>>> token :  {}", token);

        // 요청 헤더에 토큰이 존재 하지 않거나,
        if (token.isEmpty()) {
            log.info(">>> 요청헤더에 토큰이 존재하지 않음!!!!!!!!!!!!!!!");
            throw new CustomException(NOT_EXIST_TOKEN);
        }

        // 유효성 검사 (만료 여부)을 실패 했을 때 Exception
        if (!jwtTokenProvider.validateToken(token)) {
            log.info(">>> 만료된 토큰임!!!!!!!!!!!!!1");
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        return true;
    }
}
