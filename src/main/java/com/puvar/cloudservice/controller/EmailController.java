package com.puvar.cloudservice.controller;

import com.puvar.cloudcommon.common.constants.PlainResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * @param email 给指定email发送邮件
     * @return
     */
    @GetMapping("sendEmailToPerson")
    public PlainResponse sendEmailToPerson(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(email);
        mailMessage.setSubject("测试邮件");
        mailMessage.setText("这是一封测试邮件");
        try {
            mailSender.send(mailMessage);
            log.info("sendEmailToPerson is success. to person is {}", email);
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
            return PlainResponse.errorResponse();
        }
        return PlainResponse.successResponse();
    }
}
