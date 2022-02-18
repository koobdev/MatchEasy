//package koo.project.matcheasy.filter;
//
//import koo.project.matcheasy.common.SessionConst;
//import koo.project.matcheasy.common.ClientUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.PatternMatchUtils;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Slf4j
//public class LoginFilter implements Filter {
//
//    private static final String[] whitelist = {"/", "/login", "/logout", "/members/add", "/css/*"};
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String requestURI = httpRequest.getRequestURI();
//
//        try {
//            log.info("인증 체크 필터 시작 : {}", requestURI);
//            if(isNotWhiteList(requestURI)){
//                log.info("인증 체크 로직 : {}", requestURI);
//
//                HttpSession session = httpRequest.getSession(false);
//                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
//                    log.info("미인증 사용자 요청 : {}", requestURI);
//                    log.info("미인증 요청 클라이언트 IP : {}", ClientUtils.getClientIpAddress(httpRequest));
//                    httpResponse.sendRedirect("/login?redirectURI=" + requestURI);
//                    return;
//                }
//            }
//
//            chain.doFilter(request, response);
//        }catch (Exception e){
//            throw e;
//        }finally {
//            log.info("인증 체크 필터 종료 : {}", requestURI);
//        }
//    }
//
//    private boolean isNotWhiteList(String requestUri){
//        return !PatternMatchUtils.simpleMatch(whitelist, requestUri);
//    }
//}
