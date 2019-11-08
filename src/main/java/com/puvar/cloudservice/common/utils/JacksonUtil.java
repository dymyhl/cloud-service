package com.puvar.cloudservice.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/***
 * 类型转换工具类
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
public class JacksonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    private static JsonFactory JSON_FACTORY = new JsonFactory();
    private static ObjectMapper OBJECT_MAPPER = createObjectMapper();
    private static final String DATE_PATTERN = DateUtil.LONG_FORMAT;

    /**
     * 创建一个自定义的JSON ObjectMapper
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_PATTERN));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return objectMapper;
    }

    /**
     * 将对象转换为JSON字符串
     */
    public static <T> String toJson(T value) {
        if (value == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = JSON_FACTORY.createGenerator(sw);
            OBJECT_MAPPER.writeValue(gen, value);
            return sw.toString();
        } catch (IOException e) {
            LOGGER.error("object to json exception!", e);
        } finally {
            if (gen != null) {
                try {
                    gen.close();
                } catch (IOException e) {
                    LOGGER.warn("Exception occurred when closing JSON generator!", e);
                }
            }
        }
        return null;
    }

    /**
     * 将JSON字符串转换为指定对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String jsonString, Class<T> valueType, Class<?>... itemTypes) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        // 返回值加强制转换是因为scmpf编译机JDK版本jdk1.6.0_20的BUG，编译时会出错，在jdk1.6.0_25之后已修复
        try {
            if (itemTypes.length == 0) {
                // 非集合类型
                return (T) OBJECT_MAPPER.readValue(jsonString, valueType);
            } else {
                // 集合类型, 如List,Map
                JavaType javaType =
                        OBJECT_MAPPER.getTypeFactory().constructParametrizedType(valueType, valueType, itemTypes);
                return (T) OBJECT_MAPPER.readValue(jsonString, javaType);
            }
        } catch (Exception e) {
            LOGGER.error("json to object exception!", e);
            return null;
            //throw new RisRuntimeException(RetCodeEnum.ILLEGAL_ARGUMENT, e, jsonString);
        }
    }

    /**
     * 自定义TypeReference，解决复杂嵌套结构的转换
     *
     * @param jsonString
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String jsonString, TypeReference<T> type) {
        if (StringUtils.isEmpty(jsonString) && type == null) {
            return null;
        }
        try {
            return (T) OBJECT_MAPPER.readValue(jsonString, type);
        } catch (Exception e) {
            LOGGER.error("json to object exception!", e);
            return null;
        }
    }
}
