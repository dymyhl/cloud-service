package com.puvar.cloudservice.common.annotion;

import java.lang.annotation.*;

/***
 * 记录日志标记
 * @Auther: dingyuanmeng
 * @Date: 2019/9/25
 * @version : 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String desc() default "";
}
