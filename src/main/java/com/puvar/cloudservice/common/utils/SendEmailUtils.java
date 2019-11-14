package com.puvar.cloudservice.common.utils;

import com.puvar.cloudservice.domain.EmailParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/***
 * 发送邮件工具类
 * @Auther: dingyuanmeng
 * @Date: 2019/11/14
 * @version : 1.0
 */
@Slf4j
@Component
public class SendEmailUtils {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    public void thymeleafEmail(String[] to, String subject, EmailParam emailParam, String template) throws MessagingException {
        log.info("通过发送邮件工具类发送邮件，接收人={},邮件主题={},邮件内参数={},模板名称={}", to, subject, emailParam, template);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        // 利用 Thymeleaf 模板构建 html 文本
        Context ctx = new Context();
        // 给模板的参数的上下文
        ctx.setVariable("emailParam", emailParam);
        // 执行模板引擎，执行模板引擎需要传入模板名、上下文对象
        // Thymeleaf的默认配置期望所有HTML文件都放在 **resources/templates ** 目录下，以.html扩展名结尾。
        // String emailText = templateEngine.process("email/templates", ctx);
        String emailText = templateEngine.process(template, ctx);
        mimeMessageHelper.setText(emailText, true);
        // FileSystemResource logoImage= new FileSystemResource("D:\\image\\logo.jpg");
        // 绝对路径
        // FileSystemResource logoImage = new FileSystemResource(imgPath);
        // 相对路径，项目的resources路径下
        // ClassPathResource logoImage = new ClassPathResource("static/image/logonew.png");
        // 添加附件,第一个参数表示添加到 Email 中附件的名称，第二个参数是图片资源
        // 一般图片调用这个方法
        // mimeMessageHelper.addInline("logoImage", logoImage);
        // 一般文件附件调用这个方法
        // mimeMessageHelper.addAttachment("logoImage", resource);
        mailSender.send(mimeMessage);
    }
}
