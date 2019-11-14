package com.puvar.cloudservice.service.feign;

import com.puvar.cloudserviceapi.domain.UserApi;

/***
 * MqServerInterface接口服务降级
 * @Auther: dingyuanmeng
 * @Date: 2019/11/3
 * @version : 1.0
 */
public class MqServerInterfaceFallBack implements MqServerInterface {
    @Override
    public void lcnTestDB() {
    }

    @Override
    public String testMq(String name) {
        return null;
    }

    @Override
    public UserApi testFeign(UserApi userApi) {
        return null;
    }
}
