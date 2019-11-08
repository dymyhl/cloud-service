package com.puvar.cloudservice.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.puvar.cloudcommon.common.constants.RequestWrapper;
import com.puvar.cloudcommon.common.util.ResponseUtil;
import com.puvar.cloudservice.common.enums.ErrorCode;
import com.puvar.cloudservice.common.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 拦截器进行接口验签
 * sign=MD5(data)
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
@Component
public class SignInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(SignInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        try {

            String bodyString = RequestWrapper.getBodyString(request);
            JSONObject body = JSONObject.parseObject(bodyString);

            String timeStamp = body.getString("timeStamp");
            String data = body.getString("data");
            String source = body.getString("source");
            String sign = body.getString("sign");

            if (StringUtils.isEmpty(timeStamp)) {
                log.info("request timeStamp is empty");
                ResponseUtil.writeResponse(response, ErrorCode.TIMESTAMP_EMPTY.getDesc());
                return false;
            }
            if (StringUtils.isEmpty(source)) {
                log.info("request source is empty");
                ResponseUtil.writeResponse(response, ErrorCode.SOURCE_EMPTY.getDesc());
                return false;
            }
            String MD5Data = MD5Util.getMD5(data);
            if (StringUtils.isEmpty(sign) || !MD5Data.equalsIgnoreCase(sign)) {
                log.info("request sign is Empty or illegal");
                ResponseUtil.writeResponse(response, ErrorCode.SIGN_ILLEGAL.getDesc());
                return false;
            }
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
