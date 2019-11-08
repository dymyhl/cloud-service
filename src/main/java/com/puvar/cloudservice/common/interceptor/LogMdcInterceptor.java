package com.puvar.cloudservice.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 拦截器实现日志记录功能，一个请求的全部过程一个logId(也可aop实现)
 * sign=MD5(data)
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
//@Component
public class LogMdcInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LogMdcInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = "logId_" + System.currentTimeMillis();
        MDC.put("logid", requestId);
        logger.info("request url:{}, params:{}", request.getRequestURI(),
                request.getParameterMap());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        MDC.clear();
    }
}
