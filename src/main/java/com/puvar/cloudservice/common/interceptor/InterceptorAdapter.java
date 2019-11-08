package com.puvar.cloudservice.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * 注册自定义拦截器
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
@SpringBootConfiguration
public class InterceptorAdapter implements WebMvcConfigurer {

    @Autowired
    private SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor).addPathPatterns("/condition/**").excludePathPatterns("/error/**")
                .excludePathPatterns("/swagger-resource/**").excludePathPatterns("/swagger-ui.html/**");
        // registry.addInterceptor(logMdcInterceptor).addPathPatterns("/**");
    }
}
