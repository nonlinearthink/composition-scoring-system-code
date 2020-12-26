package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * 邮件服务
 *
 * @author nonlinearthink
 */
@Service
@EnableAsync
public class MailService {
    /**
     * 发件人，从项目配置中获取
     */
    @Value("${spring.mail.username}")
    private String from;
    /**
     * 邮件发送类
     */
    private final JavaMailSender mailSender;
    private final Logger logger;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        logger = LogManager.getLogger(LogCategory.BUSINESS.getPosition());
    }

    /**
     * 发送简单邮件(内容仅支持字符串)
     *
     * @param to      收件人
     * @param subject 主体
     * @param content 内容
     */
    @Async
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置发件人
        message.setFrom(from);
        // 设置收件人
        message.setTo(to);
        // 设置主题
        message.setSubject(subject);
        // 设置内容
        message.setText(content);
        // 发送
        logger.info("发送邮件 " + subject + " 到 " + to);
        mailSender.send(message);
    }
}
