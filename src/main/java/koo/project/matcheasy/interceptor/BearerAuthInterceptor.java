package koo.project.matcheasy.interceptor;

import koo.project.matcheasy.exception.CustomException;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static koo.project.matcheasy.exception.ErrorCode.INVALID_AUTH_TOKEN;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(">>> interceptor.preHandle 호출");
        String token = authExtractor.extract(request, "Bearer");
        if (StringUtils.isEmpty(token)) {
            return true;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }

        String name = jwtTokenProvider.getSubject(token);
        System.out.println(">>> access token : " + name);
        request.setAttribute("name", name);
        return true;
    }
}
