package com.email.api.emailapi.service;

import com.email.api.emailapi.helper.EmailHelper;
import com.email.api.emailapi.model.MailModel;
import com.email.api.emailapi.util.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailHelper emailHelper;

    Logger logger = (Logger) LoggerFactory.getLogger(EmailService.class);
    public void sendEmail( String to, String subject, String message){

        logger.info("Mail sending start");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("mittuparmar00@gmail.com");

        mailSender.send(simpleMailMessage);
        logger.info("Mail send");

    }

    public void sendEmail(String to, String subject, String text, MultipartFile attachment){

        logger.info("Mail sending start to : " + to);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            if (attachment!=null)helper.addAttachment(attachment.getOriginalFilename(),attachment);

            mailSender.send(message);
            logger.info("Mail send");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public Map<String, List<MailModel>> sendBulkEmails(List<MailModel> mailModelList, MultipartFile attachment){


        Map<String, List<MailModel>> statusMap = new HashMap();
        statusMap.put("SuccessList", new ArrayList<>());
        statusMap.put("RejectList", new ArrayList<>());

        mailModelList.forEach(mailModel ->
                {
                    if (mailModel.getTo() == null || mailModel.getSubject() == null || mailModel.getText() ==null ||
                            mailModel.getTo().trim().isEmpty() || mailModel.getSubject().trim().isEmpty() || mailModel.getText().trim().isEmpty()||
                            !EmailUtils.isValidEmail(mailModel.getTo())) {
                        statusMap.get("RejectList").add(mailModel);
                    }else {
                        this.sendEmail(mailModel.getTo(),mailModel.getSubject(),mailModel.getText(),attachment);
                        statusMap.get("SuccessList").add(mailModel);
                    }
                }
        );
    return statusMap;
    }

    public Map<String, List<MailModel>> sendEmails(MultipartFile excelFile, String subject, String template, MultipartFile attachment) {
        List<MailModel> mailModelList = emailHelper.convertExcelToList( excelFile,  subject,  template, attachment);
        return sendBulkEmails(mailModelList, attachment);
    }
}
