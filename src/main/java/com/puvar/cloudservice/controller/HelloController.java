package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.domain.User;
import com.puvar.cloudservice.service.springcloud.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(value = "HelloController restful api")
@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    public DiscoveryClient client;
    @Autowired
    public HelloService helloService;
    @Value("${log.level}")
    public String logLevel;

    @ApiOperation(value = "提供外部接口，有延迟", notes = "提供外部接口")
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public PlainResponse helloIndex() throws InterruptedException {
        int sleepTime = new Random().nextInt(3000);
        log.info("helloIndex sleepTime is = " + sleepTime);
        Thread.sleep(sleepTime);
        List<ServiceInstance> instances = client.getInstances("hello-MqServerExternalInterface");
        instances.forEach(p -> {
            System.out.println("host = " + p.getHost() + " , serviceId = " + p.getServiceId() + " , port = " + p.getPort());
        });
        return PlainResponse.successResponse();
    }

    @ApiOperation(value = "简单hello接口", notes = "say hell")
    @RequestMapping(value = "simpleHello", method = RequestMethod.POST)
    public PlainResponse simpleHello(@RequestParam String name) {
        log.info("simpleHello receive param = " + name);
        return PlainResponse.successDataResponse(name);
    }

    @ApiOperation(value = "复杂hello接口", notes = "say hell")
    @RequestMapping(value = "complexHello", method = RequestMethod.POST)
    public PlainResponse complexHello(@RequestBody User user) {
        log.info("complexHello receive param = " + user.toString());
        return PlainResponse.successDataResponse(user);
    }

    // 数据库操作
    @ApiOperation(value = "获取所有用户接口", notes = "user list")
    @RequestMapping(value = "helloTestList", method = RequestMethod.GET)
    public PlainResponse helloTestList() {
        List<User> lists = helloService.helloTestList();
        return PlainResponse.successDataResponse(lists);
    }

    @ApiOperation(value = "获取所有用户接口", notes = "user list")
    @RequestMapping(value = "helloList", method = RequestMethod.GET)
    public PlainResponse helloList() {
        List<User> lists = helloService.helloList();
        return PlainResponse.successDataResponse(lists);
    }

    @ApiOperation(value = "添加用户接口", notes = "add user")
    @RequestMapping(value = "addUser", method = RequestMethod.GET)
    public PlainResponse addUser() throws Exception {
        User user = new User();
        user.setName("test");
        user.setAge(11);
        return PlainResponse.successDataResponse(helloService.addUser(user));
    }
}
