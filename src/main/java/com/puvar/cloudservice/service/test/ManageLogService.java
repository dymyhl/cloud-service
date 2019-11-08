package com.puvar.cloudservice.service.test;

import com.puvar.cloudservice.domain.ManageLog;

import java.util.List;

public interface ManageLogService {
    List<ManageLog> findAll();

    void save(ManageLog manageLog);
}
