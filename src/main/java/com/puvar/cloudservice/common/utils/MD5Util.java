/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.puvar.cloudservice.common.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/***
 * MD5Util
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
public class MD5Util {
    private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

    public static String getMD5(String body) {
        String md5Str;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(body.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(String.format("%02x", result[i]));
            }
            md5Str = sb.toString();
        } catch (Exception e) {
            logger.warn(String.format("get md5 fail, text:%s, ex:%s"), body, ExceptionUtils.getStackTrace(e));
            md5Str = null;
        }
        return md5Str;
    }

    public static String getMD5(File file, String... append) {
        if (null == file) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            return getMD5(fis, append);
        } catch (Exception e) {
            logger.warn(String.format("get file md5 fail, ex:%s"), ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public static String getMD5(InputStream fis, String... append) {
        String md5Str;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            byte[] result = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(String.format("%02x", result[i]));
            }
            String fileMd5Str = sb.toString();
            for (String str : append) {
                fileMd5Str += str;
            }
            md5Str = getMD5(fileMd5Str);
        } catch (Exception e) {
            logger.warn(String.format("get inputstream md5 fail, ex:%s"), ExceptionUtils.getStackTrace(e));
            md5Str = null;
        }
        return md5Str;
    }
}
