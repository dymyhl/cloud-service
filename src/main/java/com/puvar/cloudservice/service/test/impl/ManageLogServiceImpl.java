package com.puvar.cloudservice.service.test.impl;

import com.puvar.cloudservice.dao.test.ManageLogMapper;
import com.puvar.cloudservice.domain.ManageLog;
import com.puvar.cloudservice.service.test.ManageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/11
 * @version : 1.0
 */
@Service
public class ManageLogServiceImpl implements ManageLogService {

    @Autowired
    private ManageLogMapper manageLogMapper;

    @Override
    @Transactional(value = "testTransactionManager", rollbackFor = Exception.class)
    public List<ManageLog> findAll() {
        return manageLogMapper.findAll();
    }

    @Override
    public void save(ManageLog manageLog) {
        manageLogMapper.save(manageLog);
    }
}
