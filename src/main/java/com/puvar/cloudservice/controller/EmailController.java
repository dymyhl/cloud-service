package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.common.enums.EmailTemplate;
import com.puvar.cloudservice.common.utils.SendEmailUtils;
import com.puvar.cloudservice.domain.EmailParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/***
 * 发送邮件controller
 * @Auther: dingyuanmeng
 * @Date: 2019/11/11
 * @version : 1.0
 */
@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private SendEmailUtils sendEmailUtils;

    /**
     * @param email 给指定email发送邮件
     * @return
     */
    @GetMapping("sendEmailToPerson")
    public PlainResponse sendEmailToPerson(String email) throws MessagingException {
        EmailParam emailParam = new EmailParam();
        emailParam.setStuName("丁远猛");
        emailParam.setItemName("手机");
        emailParam.setUpdateContent("更新内容");
        emailParam.setUpdateDate("2019-11-14");
        emailParam.setUpdatePerson("丁远猛");
        String[] emails = {email};
        sendEmailUtils.thymeleafEmail(emails, "springboot测试发送lark", emailParam, EmailTemplate.NOTICETEMPLATE.getTemplateName());
        return PlainResponse.successResponse();
    }
}
