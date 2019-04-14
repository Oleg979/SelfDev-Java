package com.oleg.restdemo.services.taskMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskMessageService {
    private JavaMailSender mailSender;
    private TaskMessageContentBuilder mailContentBuilder;

    @Autowired
    public TaskMessageService(JavaMailSender mailSender, TaskMessageContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void prepareAndSend(String recipient, String name, String title) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("olegsolovev506@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Task: " + title);
            String content = mailContentBuilder.build(name, title);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            log.warn("Error while sending message to " + recipient);
        }
        log.info("Email sent to " + recipient);
    }
}
