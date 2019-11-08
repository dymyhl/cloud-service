package com.puvar.cloudservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/***
 * 跨域
 * @Auther: dingyuanmeng
 * @Date: 2019/8/19
 * @version : 1.0
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildCorsConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildCorsConfig() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        return configuration;
    }
    // 默认切面(方法，class),可替换methodInteceptor
    /*@Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor3(SystemLogInteceptor testM) {
        Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(SystemLog.class);
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(testM);
        advisor.setOrder(1);
        return advisor;
    }*/


}
