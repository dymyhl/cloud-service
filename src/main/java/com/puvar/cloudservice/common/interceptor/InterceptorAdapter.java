package com.puvar.cloudservice.common.interceptor;

import com.puvar.cloudcommon.interceptor.LoginInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * 注册自定义拦截器
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
@SpringBootConfiguration
public class InterceptorAdapter implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext context;
    @Value("${sso.switch}")
    public boolean ssoSwitch;

    @Autowired
    private SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor).addPathPatterns("/condition/**").excludePathPatterns("/error/**")
                .excludePathPatterns("/swagger-resource/**").excludePathPatterns("/swagger-ui.html/**");
        // 判断本服务是否可直接被调用，不通过网关
        if (ssoSwitch) {
            registry.addInterceptor(context.getBean(LoginInterceptor.class)).addPathPatterns("/**")
                    .excludePathPatterns("/login/**")
                    .excludePathPatterns("/sso/**")
                    .excludePathPatterns("/error/**")
                    .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**");
        }
        // registry.addInterceptor(logMdcInterceptor).addPathPatterns("/**");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
