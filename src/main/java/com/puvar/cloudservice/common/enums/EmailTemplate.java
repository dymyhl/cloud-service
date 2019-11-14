package com.puvar.cloudservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * email模板枚举
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
@AllArgsConstructor
public enum EmailTemplate {

    NOTICETEMPLATE("NoticeTemplate");

    @Getter
    private String templateName;
}
