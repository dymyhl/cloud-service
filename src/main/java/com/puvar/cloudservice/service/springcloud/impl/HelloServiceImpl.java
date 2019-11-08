package com.puvar.cloudservice.service.springcloud.impl;

import com.puvar.cloudservice.common.aspect.LogAspect;
import com.puvar.cloudservice.dao.springcloud.HelloMapper;
import com.puvar.cloudservice.dao.test.HelloTestMapper;
import com.puvar.cloudservice.domain.User;
import com.puvar.cloudservice.service.springcloud.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/8/14
 * @version : 1.0
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);


    @Autowired
    private HelloMapper helloMapper;
    @Autowired
    private HelloTestMapper helloTestMapper;

    @Override
    @Transactional(value = "testTransactionManager")
    public List<User> helloTestList() {
        return helloTestMapper.helloList();
    }

    @Override
    @Transactional(value = "springCloudTransactionManager")
    public List<User> helloList() {
        return helloMapper.helloList();
    }

    @Override
    @Transactional(value = "testTransactionManager")
    public boolean addUser(User user) throws SQLException {
        int count = helloMapper.addUser(user);
        int i = 1 / 0;
        return count > 0;
    }

    @Override
    //@LcnTransaction
    @Transactional(value = "springCloudTransactionManager")
    public boolean updateUser(User user) {
        int count = helloMapper.updateUser(user);
        return count > 0;
    }

}
