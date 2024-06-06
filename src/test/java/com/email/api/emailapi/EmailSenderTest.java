package com.email.api.emailapi;

import com.email.api.emailapi.helper.FileHelper;
import com.email.api.emailapi.model.MailModel;
import com.email.api.emailapi.service.EmailService;
import com.email.api.emailapi.util.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    FileHelper fileHelper;

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
    void  bulkEmailSendWithFile() throws IOException {

        System.out.println("testing");
        MailModel mailModel = new MailModel();
        mailModel.setTo("mittuparmar98262@gmail.com");
        mailModel.setSubject("subject");
        mailModel.setText("text");
        Map<String, List<MailModel>> statusMap = emailService.sendBulkEmails(List.of(mailModel), null);
        System.out.println(statusMap);
    }

    @Test
    void firstNameExtractor(){
        List<String> emails = Arrays.asList(
                "anushri.j@ipsl.co.in",
                "anchal.k.ytps@gmail.com",
                "jbhatia@forcecraver.com",
                "sj00746728@techmahindra.com",
                "hr2.avionic@gmail.com",
                "hrconvictionpranjal@gmail.com"
        );

        for (String email : emails) {
            System.out.println("Email: " + email + " => First Name: " + EmailUtils.extractFirstName(email));
        }
    }

    @Test
    void testFile(){
        String fileName = "emails.txt";
        System.out.println(fileHelper.emailExists(fileName,"mittupaemar.mp@gmail.com"));
//            System.out.println(fileHelper.readFile(fileName));
    }
}
