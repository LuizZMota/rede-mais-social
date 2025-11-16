package com.example.Controlador;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnviaEmail {
    public static void main(String[] args) {

        final String meuEmail = "seuemail@gmail.com";
        final String senhaApp = "sua_senha_app"; // senha gerada no Google

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(meuEmail, senhaApp);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(meuEmail));
            mensagem.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("destinatario@gmail.com")
            );
            mensagem.setSubject("Assunto do e-mail");
            mensagem.setText("Olá! Este é um teste de envio de e-mail em Java!");

            Transport.send(mensagem);

            System.out.println("E-mail enviado!");
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail:");
            e.printStackTrace();
        }
    }
}
