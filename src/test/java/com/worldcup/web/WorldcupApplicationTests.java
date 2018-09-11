package com.worldcup.web;

import com.worldcup.web.service.SendMailService;
import groovy.util.logging.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorldcupApplicationTests {

    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void contextLoads() {
        sendMailService.sendSimpleMail();
        /*Context context = new Context();
        context.setVariable("name", "sticker");
        String process = templateEngine.process("mail/template", context);
        sendMailService.sendHtmlMail(process);*/
    }

}
