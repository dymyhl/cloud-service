package com.puvar.cloudservice.common.constants;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * mapper配置
 * @Auther: dingyuanmeng
 * @Date: 2019/10/10
 * @version : 1.0
 */
@Data
@NoArgsConstructor
public class MapperPackage {

    public static final String springcloud_mapper = "com.puvar.cloudservice.dao.springcloud";
    public static final String springcloud_xml_mapper = "classpath:mapping/springcloud/*.xml";
    public static final String test_mapper = "com.puvar.cloudservice.dao.test";
    public static final String test_xml_mapper = "classpath:mapping/test/*.xml";

}
