package com.puvar.cloudservice.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/***
 * 根据matches条件判断是否加载bean   @conditional*
 * @Auther: dingyuanmeng
 * @Date: 2019/9/29
 * @version : 1.0
 */
public class ConditionalConfig implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return false;
    }

}
