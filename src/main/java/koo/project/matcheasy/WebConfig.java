package koo.project.matcheasy;

import koo.project.matcheasy.filter.LoginFilter;
import koo.project.matcheasy.interceptor.BearerAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                .excludePathPatterns("/", "/login", "/logout", "/members/add", "/css/**");
    }

//    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean<LoginFilter> loginFilterBean = new FilterRegistrationBean<>();
        loginFilterBean.setFilter(new LoginFilter());
        loginFilterBean.setOrder(0);
        loginFilterBean.addUrlPatterns("/*");

        return loginFilterBean;
    }

}
