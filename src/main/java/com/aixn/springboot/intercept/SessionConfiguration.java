package com.aixn.springboot.intercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zxx
 * @date 2018/12/15 22:55
 */
@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SysInterceptor()).addPathPatterns("/item/**");
    }
}
