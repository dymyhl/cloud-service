package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.common.constants.PlainRequest;
import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.common.annotion.Base64Code;
import com.puvar.cloudservice.common.annotion.SystemLog;
import com.puvar.cloudservice.common.interceptor.SignInterceptor;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * 需要验签的接口
 * @Auther: dingyuanmeng
 * @Date: 2019/9/24
 * @version : 1.0
 */
@RestController
@RequestMapping("/condition")
public class ConditionController implements BeanFactoryPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(SignInterceptor.class);

    @ApiOperation(value = "base64编码参数调用接口")
    @PostMapping("/code")
    @Base64Code(encode = false, decode = false)
    @SystemLog(desc = "日志记录接口测试")
    public PlainResponse code(@RequestBody PlainRequest request){
        log.info("ConditionController timeStamp={},data={},source={},sign={}", request.getTimeStamp(),
                request.getData(),request.getSource(),request.getSign());
        return PlainResponse.successDataResponse("success");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
