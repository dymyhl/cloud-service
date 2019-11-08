package com.puvar.cloudservice.service.feign;

import com.puvar.helloserviceapi.domain.UserApi;

/***
 * MqServerInterface接口服务降级
 * @Auther: dingyuanmeng
 * @Date: 2019/11/3
 * @version : 1.0
 */
public class MqServerInterfaceFallBack implements MqServerInterface{
    @Override
    public void lcnTestDB() {}

    @Override
    public String testMq(String name) {
        return "接口异常，请稍后重试";
    }

    @Override
    public UserApi testFeign(UserApi userApi) {
        return new UserApi().setName("接口异常，使用默认名").setAge(0);
    }
}
