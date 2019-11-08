package com.puvar.cloudservice.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/***
 * springBean工具类
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
@Component
public class ContextUtil implements ApplicationContextAware {

    public static final Logger logger = LoggerFactory.getLogger(ContextUtil.class);

    public static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtil.ctx = applicationContext;
    }

    public static boolean isInitContext = false;

    public static void setContext(ApplicationContext context) {
        if (!isInitContext) {
            ContextUtil.ctx = context;
            isInitContext = true;
        }
    }

    public static ApplicationContext getContext() {
        return ctx;
    }

    public static Object getBean(String beanName) {
        if (getContext() == null) {
            logger.error("spring context can not be found");
            return null;
        }
        if (StringUtils.isEmpty(beanName)) {
            logger.error("'beanName'can not be null");
            return false;
        }
        return getContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        if (getContext() == null) {
            logger.error("spring context can not be found");
            return null;
        }

        return getContext().getBean(clazz);
    }

    public static boolean containsBean(String beanName) {
        if (getContext() == null) {
            logger.error("spring context can not be found");
            return false;
        }
        if (StringUtils.isEmpty(beanName)) {
            logger.error("'beanName'can not be null");
            return false;
        }
        return getContext().containsBean(beanName);
    }
}