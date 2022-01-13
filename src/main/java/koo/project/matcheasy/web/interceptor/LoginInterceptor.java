package koo.project.matcheasy.web.interceptor;

import koo.project.matcheasy.web.common.ClientUtils;
import koo.project.matcheasy.web.common.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        log.info("인증 체크 인터셉터 시작 : [{}][{}]", requestURI, handler);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청 : [{}][{}]", requestURI, handler);
            log.info("미인증 요청 클라이언트 IP : [{}]", ClientUtils.getClientIpAddress(request));
            response.sendRedirect("/login?redirectURI=" + requestURI);
            return false;
        }

        return true;
    }
}
