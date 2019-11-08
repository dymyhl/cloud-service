package com.puvar.cloudservice.controller;

import com.puvar.cloudservice.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public User testGet(User user){
        return user;
    }

    @GetMapping("/testUserParam")
    public String testUserParam(String id,String name,String age,String nameValue){
        log.info("{},{},{},{}",id,name,age,nameValue);
        return "success";
    }

    @GetMapping("/testUserRequestParam")
    public String testUserRequestParam(@RequestParam String id, String name, String age, String nameValue){
        log.info("{},{},{},{}",id,name,age,nameValue);
        return "success";
    }

    // post
    @PostMapping("/testUserPost")
    public User testUserPost(User user){
        return user;
    }

    @PostMapping("/testParamPost")
    public String testParamPost(@RequestParam String name){
        return name;
    }

    @PostMapping("/testPost")
    public User testPost(@RequestBody User user){
        return user;
    }
}
