package koo.project.matcheasy;

import koo.project.matcheasy.web.filter.LoginFilter;
import koo.project.matcheasy.web.interceptor.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/logout", "/member/add", "/css/**");
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
