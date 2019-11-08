package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.domain.User;
import com.puvar.cloudservice.service.springcloud.HelloService;
import com.puvar.cloudservice.service.feign.MqServerInterface;
import com.puvar.helloserviceapi.domain.UserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * 调用mq_server提供的外部接口
 * @Auther: dingyuanmeng
 * @Date: 2019/10/9
 * @version : 1.0
 */
@RestController
@Slf4j
public class MqServerController{

    @Autowired
    private MqServerInterface mqServerInterface;
    @Autowired
    private HelloService helloService;

    @GetMapping("lcnTestDB")
    public void lcnTestDB() {
        User user = new User().setId(1).setName("test").setAge(12);
        boolean b = helloService.updateUser(user);
        log.info("hello-service执行数据库结果：{}", b);
        mqServerInterface.lcnTestDB();
    }

    @GetMapping("testMq")
    public PlainResponse testMq() {
        return PlainResponse.successDataResponse(mqServerInterface.testMq("丁远猛"));
    }

    @GetMapping("testFeign")
    public PlainResponse testFeign() {
        UserApi userApi = new UserApi();
        userApi.setAge(18);
        userApi.setName("丁远猛");
        return PlainResponse.successDataResponse(mqServerInterface.testFeign(userApi));
    }
}