package com.puvar.cloudservice.common.aspect;

import com.puvar.cloudcommon.common.constants.PlainRequest;
import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.common.constants.ManageException;
import com.puvar.cloudservice.common.annotion.Base64Code;
import com.puvar.cloudservice.common.utils.Base64Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/***
 * Aop对参数进行base64编码解码
 * @Auther: dingyuanmeng
 * @Date: 2019/8/14
 * @version : 1.0
 */
@Aspect
@Component
@Order(1)
public class Base64Aspect {

    private static final Logger log = LoggerFactory.getLogger(Base64Aspect.class);

    @Pointcut("@annotation(com.puvar.cloudservice.common.annotion.Base64Code)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(base64) && args(request)")
    public Object decodeOrEncode(ProceedingJoinPoint pjp, Base64Code base64, PlainRequest request)
            throws ManageException {
        log.info("enter into Base64Aspect data = {}", request);
        try {
            Object result = null;
            if (!base64.decode()) {
                result = pjp.proceed();
            } else {    // 解码
                String decode = Base64Util.decode(request.getData());
                log.info("data decode is {}", decode);
                Object[] args = pjp.getArgs();
                args[0] = request;
                result = pjp.proceed(args);
                log.info("Base64Aspect result {}", result);
            }

            if (!base64.encode()) {
                return result;
            } else {    // 编码
                if (result instanceof PlainResponse) {
                    PlainResponse response = (PlainResponse) result;
                    Object responseData = response.getData();
                    log.info("responseData is {}", responseData);
                    if (responseData != null) {
                        String encodeData = Base64Util.encode(responseData.toString());
                        log.info("encodeData is {}", encodeData);
                        response.setData(encodeData);
                    }
                    return response;
                }
                return null;
            }
        } catch (Throwable e) {
            throw new ManageException(getStackTrace(e));
        }
    }

    /**
     * 获取错误的堆栈信息
     *
     * @param throwable
     * @return
     */
    public String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        } finally {
            printWriter.close();
        }
    }

}
