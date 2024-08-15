package com.business.main.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{
    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.activated}")
    private Integer isEmailActivated;

    public void sendEmail(String receiver,String subject, String text) {
        // fixme: crear un email de empresa para enviar emails. Hacer la MAUTH
        if(isSendEmailActivated()){
            Session session = getSession(username, password);
            try {
                Transport.send(createMessage(session, username, receiver, subject, text));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("Email sending is not activated");
        }
    }

    private boolean isSendEmailActivated() {
        return isEmailActivated == 1;
    }

    private Message createMessage(Session session, String username, String receiver, String subject, String text) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    private Session getSession(String username, String password){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
