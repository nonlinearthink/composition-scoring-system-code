package cn.edu.zucc.se2020g11.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTests {
    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendSimpleMail("2468087928@qq.com", "批多多邮箱功能测试", "验证码: h1Wr8c(大小写敏感)");
    }

}
