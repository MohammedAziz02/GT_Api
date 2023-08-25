package com.ensah.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private  TemplateEngine templateEngine;



    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ensahterrainservice@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


    public void sendMessageWithAttachment(
            String to, String subject, String text) throws MessagingException, IOException {


        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("ensahterrainservice@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text,true);

//        Resource resource = resourceLoader.getResource("classpath:/attachments/logo.png" );
//        File fileimg = resource.getFile();
//
//        FileSystemResource file
//                = new FileSystemResource(fileimg);
//        helper.addAttachment("Invoice", file);

        ClassPathResource logoResource = new ClassPathResource("/attachments/footer.png");
        helper.addInline("logo", logoResource);

        emailSender.send(message);

    }


    public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "UTF-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            ClassPathResource imageResource = new ClassPathResource("/attachments/footer.png");
            helper.addInline("footer", imageResource);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw  new RuntimeException("Could not send email");
        }
    }
}
