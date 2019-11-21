package com.puvar.cloudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.puvar")
@EnableEurekaClient
@EnableTransactionManagement
// 开启swagger2
@EnableSwagger2
// 开启feign服务调用
@EnableFeignClients
// 开启分布式事务
//@EnableDistributedTransaction
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class CloudServiceApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(CloudServiceApplication.class, args);
    }

}
