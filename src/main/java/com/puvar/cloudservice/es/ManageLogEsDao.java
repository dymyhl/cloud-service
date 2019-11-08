package com.puvar.cloudservice.es;

import com.puvar.cloudservice.domain.ManageLogEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * es操作接口
 */
public interface ManageLogEsDao extends ElasticsearchRepository<ManageLogEs, Integer> {

}
