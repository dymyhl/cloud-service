package com.puvar.cloudservice.common.aspect;

import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.common.annotion.SystemLog;
import com.puvar.cloudservice.common.constants.ManageException;
import com.puvar.cloudservice.common.utils.JacksonUtil;
import com.puvar.cloudservice.dao.test.ManageLogMapper;
import com.puvar.cloudservice.domain.ManageLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/***
 * 打印日志切面,根据注解标识记录日志
 * @Auther: dingyuanmeng
 * @Date: 2019/8/14
 * @version : 1.0
 */
@Aspect
@Component
@Order(0)
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private ManageLogMapper manageLogMapper;

    /**
     * 切点
     */
    @Pointcut("execution(public * com.puvar.cloudservice.controller.*.*(..))")
    public void pointCut() {
    }

    /**
     * 切点前方法
     */
    @Before("pointCut()")
    public void deBefore(JoinPoint joinPoint) {
    }

    /**
     * 切点后方法
     */
    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
    }

    /**
     * 后置异常通知
     *
     * @param jp
     */
    @AfterThrowing("pointCut()")
    public void throwss(JoinPoint jp) {
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     *
     * @param jp
     */
    @After("pointCut()")
    public void after(JoinPoint jp) {
        MDC.clear();
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * 环绕通知可以控制返回对象，即你可以返回一个与目标对象完全不同的返回值，虽然这很危险，但是你却可以办到。而后置方法是无法办到的，因为他是在目标方法返回值后调用
     *
     * @param pjp
     * @return
     * @throws ManageException
     * @throws Exception
     */
    @Around("pointCut()")
    public Object arround(ProceedingJoinPoint pjp) {
        StringBuilder sb = new StringBuilder();
        long start = System.currentTimeMillis();
        ManageLog manageLog = new ManageLog();
        Object result;
        boolean flag;
        String jsonParam = "";
        SystemLog systemLog;
        try {
            Signature sig = pjp.getSignature();
            // 使用stopwatch计时
            StopWatch sw = new StopWatch();
            sw.start(sig.getName());
            // 根据request获取请求参数
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            if (pjp.getArgs() != null && pjp.getArgs().length > 0) {
                jsonParam = JacksonUtil.toJson(pjp.getArgs()[0]);
            }
            // 生成logId,拼接打印参数
            String logid = "logId_" + System.currentTimeMillis();
            MDC.put("logid", logid);
            sb.append("\n接口地址:" + request.getRequestURL());
            sb.append("\n接口方式:" + request.getMethod());
            sb.append("\n接口路径:" + sig.getDeclaringTypeName() + "." + sig.getName());
            sb.append("\n接口参数:" + jsonParam);
            // 记录日志信息
            manageLog.setLogId(logid);
            manageLog.setRequestUrl(request.getRequestURL().toString());
            manageLog.setRequestMethod(request.getMethod());
            manageLog.setSystem(appName);
            manageLog.setParamValue(jsonParam);
            // 获得请求目标方法
            MethodSignature msig = (MethodSignature) sig;
            Object target = pjp.getTarget();
            Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            flag = method.isAnnotationPresent(SystemLog.class);
            systemLog = method.getAnnotation(SystemLog.class);
        } catch (Exception e) {
            log.error("日志切面Aop异常：{}", e);
            return PlainResponse.errorResponse();
        }
        try {
            result = pjp.proceed();
            // 接收到请求，记录请求内容
            String jsonResult = JacksonUtil.toJson(result);
            sb.append("\n对外接口返回:" + jsonResult);
            // 处理完请求，返回内容
            log.info(sb.toString());
            manageLog.setResultValue(jsonResult);
            dumpInfo(flag, systemLog, System.currentTimeMillis() - start, manageLog, true);
            return result;
        } catch (Throwable e) {
            sb.append("\n对外接口返回异常:" + getStackTrace(e));
            log.error(sb.toString(), e);
            manageLog.setExceptionValue(e.toString());
            dumpInfo(flag, systemLog, System.currentTimeMillis() - start, manageLog, false);
            return PlainResponse.errorResponse();
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

    public void dumpInfo(boolean flag, SystemLog systemLog, long ms, ManageLog manageLog, boolean isSuccess) {
        try {
            if (flag) {
                // 日志描述
                String desc = systemLog.desc();
                int timeLength = (int) ms;
                manageLog.setOperateDesc(desc);
                manageLog.setTimeLength(timeLength);
                String paramValue = "状态:[" + isSuccess + "],参数：" + manageLog.getParamValue();
                manageLog.setParamValue(paramValue);
                manageLogMapper.save(manageLog);
            }
        } catch (Exception e) {
            log.error("记录日志异常 error {}", e);
        }
    }
}
