package com.email.api.emailapi;

import com.email.api.emailapi.model.MailModel;
import com.email.api.emailapi.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    void emailSendTest(){
        System.out.println("Sending email");
        emailService.sendEmail("mittuparmar98262@gmail.com", "email sending test", "This is actual email");
    }

    @Test
    void  emailSendWithFile(){
        emailService.sendEmail(
                "mittuparmar98262@gmail.com",
                "email sending test",
                "This is actual email"
        );
    }

    @Test
    void  bulkEmailSendWithFile(){

        System.out.println("testing");
        MailModel mailModel = new MailModel();
        mailModel.setTo("mittuparmar98262@gmail.com");
        mailModel.setSubject("subject");
        mailModel.setText("text");
        Map<String, List<MailModel>> statusMap = emailService.sendBulkEmails(List.of(mailModel), null);
        System.out.println(statusMap);
    }
}
