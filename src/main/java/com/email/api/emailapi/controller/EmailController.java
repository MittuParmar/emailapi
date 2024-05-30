package com.email.api.emailapi.controller;

import com.email.api.emailapi.model.MailModel;
import com.email.api.emailapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/api/send-emails")
    @ResponseBody
    public Map<String, List<MailModel>> sendEmails(
            @RequestParam("excelFile") MultipartFile excelFile,
            @RequestParam("subject") String subject,
            @RequestParam("template") String template,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

        System.out.println("This is " + template);
        return emailService.sendEmails(excelFile,subject,template,attachment);
    }
}
