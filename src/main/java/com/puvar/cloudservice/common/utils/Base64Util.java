package com.puvar.cloudservice.common.utils;


import org.apache.commons.codec.binary.Base64;

/***
 * Base64工具类
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
public class Base64Util {

    // 解码
    public static String decode(String decode){
        byte[] bytes = decode.getBytes();
        return new String(Base64.decodeBase64(bytes));
    }

    // 编码
    public static String encode(String encode){
        byte[] bytes = encode.getBytes();
        return new String(Base64.encodeBase64(bytes));
    }
}
