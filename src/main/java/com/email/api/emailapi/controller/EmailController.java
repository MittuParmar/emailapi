package com.email.api.emailapi.controller;

import com.email.api.emailapi.model.MailModel;
import com.email.api.emailapi.service.EmailService;
import com.email.api.emailapi.service.ExcelService;
import com.email.api.emailapi.util.EmailUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    ExcelService excelService;

    @PostMapping("/api/send-emails")
    public Map<String, List<MailModel>> sendEmails(
            @RequestParam("excelFile") MultipartFile excelFile,
            @RequestParam("subject") String subject,
            @RequestParam("template") String template,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) throws IOException {

        System.out.println("This is " + template);
        return emailService.sendEmails(excelFile,subject,template,attachment);
    }

    @PostMapping("/api/emails/process")
    public List<Map<String, String>> processEmails(@RequestBody List<String> emails) {
        List<Map<String, String>> emailNames = new ArrayList<>();
        Set<String> emailSet = new HashSet<>(emails);
        for (String email : emailSet) {
            emailNames.add(Map.of("email", email, "name", EmailUtils.extractFirstName(email)));
        }
        return emailNames;
    }

    @PostMapping("/api/emails/submit")
    public Map<String, List<MailModel>> submitEmails(
            @RequestPart("emails") List<Map<String, String>> emailNames,
            @RequestPart("template") String template,
            @RequestPart("heading") String heading,
            @RequestPart("attachment") MultipartFile attachment) throws MessagingException, IOException {

        List<MailModel> mailModelList = new ArrayList<>();
        for (Map<String, String> emailName : emailNames) {
            mailModelList.add(new MailModel(emailName.get("email"), heading, emailName.get("name"), template));
        }
        return emailService.sendBulkEmails(mailModelList, attachment);
    }

//    @PostMapping("/api/emails/submit")
//    public ResponseEntity<byte[]> submitEmails(@RequestBody List<Map<String, String>> emailNames) throws IOException {
//        List<String> emails = new ArrayList<>();
//        List<String> names = new ArrayList<>();
//
//        System.out.println(emailNames);
//
//        for (Map<String, String> emailName : emailNames) {
//            emails.add(emailName.get("email"));
//            names.add(emailName.get("name"));
//        }
//
//        ByteArrayInputStream in = excelService.generateExcelFile(emails, names);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=emails.xlsx");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(in.readAllBytes());
//    }
}
