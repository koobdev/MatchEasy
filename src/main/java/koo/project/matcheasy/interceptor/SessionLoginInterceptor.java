package koo.project.matcheasy.interceptor;

import koo.project.matcheasy.common.ClientUtils;
import koo.project.matcheasy.common.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class SessionLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        log.info("인증 체크 인터셉터 시작 : [{}]", requestURI);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청");
            log.info("미인증 요청 클라이언트 IP : [{}]", ClientUtils.getClientIpAddress(request));
            try {
                response.sendRedirect("/login?redirectURI=" + requestURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;
    }
}
