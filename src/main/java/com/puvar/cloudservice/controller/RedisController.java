package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudcommon.domain.ManageUser;
import com.puvar.cloudservice.common.client.RedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/***
 * redisController
 * @Auther: dingyuanmeng
 * @Date: 2019/8/22
 * @version : 1.0
 */
@Api(value = "RedisController restful api")
@RestController
@RequestMapping("/redis")
public class RedisController {
    private static final Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "测试redis接口", notes = "test redis")
    @RequestMapping(value = "testRedis", method = RequestMethod.GET)
    public PlainResponse testRedis() {
        ManageUser user = new ManageUser().setUserName("");
        redisClient.setString("dym", user);
        return PlainResponse.successResponse();
    }
}
