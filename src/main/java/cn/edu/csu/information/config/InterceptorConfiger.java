package cn.edu.csu.information.config;

import cn.edu.csu.information.config.interecptor.AdminInterceptor;
import cn.edu.csu.information.config.interecptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfiger implements WebMvcConfigurer {

    @Resource
    private UserInterceptor userInterceptor;

    @Resource
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userInterceptor).excludePathPatterns("/*").excludePathPatterns("/news/*","/**.ico","/webjars/**","/v2/**","/configuration/**","/open/**")
                .excludePathPatterns("/passport/*").excludePathPatterns("/static/**").excludePathPatterns("/admin/login");

        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");

    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }
}
