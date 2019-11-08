package com.puvar.cloudservice.service.feign;

import com.puvar.helloserviceapi.MqServerExternalInterface.ExternalInterfaceApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "mq-server",fallback = MqServerInterfaceFallBack.class)
public interface MqServerInterface extends ExternalInterfaceApi {

    @PostMapping("lcnTestDB")
    void lcnTestDB();

}
