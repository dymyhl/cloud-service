package com.puvar.cloudservice.dao.springcloud;

import com.puvar.cloudservice.domain.MgTask;

import java.util.List;

public interface MgTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MgTask record);

    int insertSelective(MgTask record);

    MgTask selectByPrimaryKey(Long id);

    List<MgTask> selectAll();

    int updateByPrimaryKeySelective(MgTask record);

    int updateByPrimaryKey(MgTask record);
}