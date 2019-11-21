package com.puvar.cloudservice.common.enums;

import lombok.Getter;

/***
 * cloud-service端统一错误码
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
public enum ErrorCode {

    SUCCESS("0000", "success"),
    TIMESTAMP_EMPTY("0001", "时间戳为空"),
    SOURCE_EMPTY("0002", "来源为空"),
    SIGN_ILLEGAL("0002", "sign为空或者不合法");

    @Getter
    private String code;
    @Getter
    private String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
