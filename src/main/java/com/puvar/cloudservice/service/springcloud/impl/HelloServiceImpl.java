package com.puvar.cloudservice.service.springcloud.impl;

import com.puvar.cloudcommon.domain.ManageUser;
import com.puvar.cloudservice.common.aspect.LogAspect;
import com.puvar.cloudservice.dao.springcloud.ManageUserMapper;
import com.puvar.cloudservice.service.springcloud.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ManageUserMapper helloMapper;

    @Override
    @Transactional(value = "springCloudTransactionManager")
    public List<ManageUser> helloList() {
        return helloMapper.helloList();
    }
}
