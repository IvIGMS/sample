package com.business.main.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    public void sendEmail(String receiver, String text) {
        // fixme: crear un email de empresa para enviar emails. Hacer la MAUTH
        Session session = getSession(username, password);
        try {
            Transport.send(createMessage(session, username, receiver, "Pruebas", text));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message createMessage(Session session, String username, String receiver, String subject, String text) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject("Prueba");
        message.setText("Esto es una prueba del mensaje");
        return message;
    }

    private Session getSession(String username, String password){
        // Configuración de las propiedades para la conexión SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Obtener la sesión por medio de la autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }
}
