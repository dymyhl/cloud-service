package com.puvar.cloudservice.common.annotion;

import java.lang.annotation.*;

/***
 * base64解码编码
 * @Auther: dingyuanmeng
 * @Date: 2019/9/25
 * @version : 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Base64Code {

    boolean encode(); // 接口返回值是否编码

    boolean decode(); // 接口参数是否数解码

}
