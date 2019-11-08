package com.puvar.cloudservice.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/***
 * es实体对象
 * @Auther: dingyuanmeng
 * @Date: 2019/8/26
 * @version : 1.0
 */
// indexName同数据库，type同表
@Document(indexName = "slumps", type = "goods")
@Data
@Accessors(chain = true)
public class Slump implements Serializable {

    @Id
    private Integer slumpId;
    /**
     * type:数据类型。
     * index:FieldIndex.not_analyzed(不分词，根据原词索引)、FieldIndex.analyzed根据分词器分词、FieldIndex.no不索引，查不到。
     * store:es是否会存储field内容
     */
    //@Field(type = FieldType.Long, index = FieldIndex.not_analyzed, store = true)
    private String slumpName;
    private String slumpColor;
    private String slumpFlavor;
    private boolean slumpFlag;
}
