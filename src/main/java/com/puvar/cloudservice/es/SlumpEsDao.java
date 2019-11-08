package com.puvar.cloudservice.es;

import com.puvar.cloudservice.domain.Slump;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * es操作接口
 */
public interface SlumpEsDao extends ElasticsearchRepository<Slump, Integer> {

}
