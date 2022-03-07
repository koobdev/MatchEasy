package koo.project.matcheasy;

import koo.project.matcheasy.interceptor.AuthorizationExtractor;
import koo.project.matcheasy.interceptor.BearerAuthInterceptor;
import koo.project.matcheasy.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.AbstractAnsiTrimEmulationFunction;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

//@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final BearerAuthInterceptor bearerAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Session로그인 Interceptor
//        registry.addInterceptor(new LoginInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/logout", "/members/add", "/css/**");

        System.out.println("인터셉터 시작");
        registry.addInterceptor(bearerAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/form/**")
                .excludePathPatterns("/home")
                .excludePathPatterns("/main")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/token/login")
                .excludePathPatterns("/token/reIssue")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/members/add")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/*.ico")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/templates/**");
    }

//    @Bean
//    public FilterRegistrationBean loginFilter(){
//        FilterRegistrationBean<LoginFilter> loginFilterBean = new FilterRegistrationBean<>();
//        loginFilterBean.setFilter(new LoginFilter());
//        loginFilterBean.setOrder(0);
//        loginFilterBean.addUrlPatterns("/*");
//
//        return loginFilterBean;
//    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/");
    }
}
