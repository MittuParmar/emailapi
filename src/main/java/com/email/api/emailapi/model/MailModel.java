package com.email.api.emailapi.model;

import org.springframework.web.multipart.MultipartFile;

public class MailModel {
    private String to;
    private String subject;
    private String name;
    private String text;

    public MailModel() {
    }

    public MailModel(String to, String subject, String name, String text) {
        this.to = to;
        this.subject = subject;
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MailModel{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
