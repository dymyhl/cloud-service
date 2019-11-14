package com.puvar.cloudservice.domain;

import lombok.Data;

/***
 * 邮件实体类
 * @Auther: dingyuanmeng
 * @Date: 2019/11/14
 * @version : 1.0
 */
@Data
public class EmailParam {

    public String updateContent;
    public String itemName;
    public String stuName;
    public String updatePerson;
    public String updateDate;
}
