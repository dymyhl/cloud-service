package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.domain.ManageUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/11/1
 * @version : 1.0
 */
@Slf4j
@RestController
public class TestAnnotationController {

    // get
    @GetMapping("/testUser")
    public ManageUser testGet(ManageUser user) {
        return user;
    }

    @GetMapping("/testUserParam")
    public String testUserParam(String id, String name, String age, String nameValue) {
        log.info("{},{},{},{}", id, name, age, nameValue);
        return "success";
    }

    @GetMapping("/testUserRequestParam")
    public String testUserRequestParam(@RequestParam String id, String name, String age, String nameValue) {
        log.info("{},{},{},{}", id, name, age, nameValue);
        return "success";
    }

    // post
    @PostMapping("/testUserPost")
    public ManageUser testUserPost(ManageUser user) {
        return user;
    }

    @PostMapping("/testParamPost")
    public String testParamPost(@RequestParam String name) {
        return name;
    }

    @PostMapping("/testPost")
    public ManageUser testPost(@RequestBody ManageUser user) {
        return user;
    }
}
