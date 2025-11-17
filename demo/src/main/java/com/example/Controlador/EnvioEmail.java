package com.example.Controlador;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EnvioEmail {
    private final String meuEmail;
    private final String senhaApp;

    public EnvioEmail(String meuEmail, String senhaApp) {
        this.meuEmail = meuEmail;
        this.senhaApp = senhaApp;
    }

    public void enviarEmail(String destinatario, String assunto, String corpo) {

        Properties props = new Properties();//Aqui trata das configurações do servidor SMTP do gmail
        props.put("mail.smtp.auth", "true");//autenticação
        props.put("mail.smtp.starttls.enable", "true");//criptografia
        props.put("mail.smtp.host", "smtp.gmail.com");//servidor SMTP
        props.put("mail.smtp.port", "587");//porta padrao de envio

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//isso envia o user + a senha para o gmail para validacao
                return new PasswordAuthentication(meuEmail, senhaApp);
            }
        });

        try {//Aqui será onde vai ser montado o email
            Message mensagem = new MimeMessage(session);//quem envia
            mensagem.setFrom(new InternetAddress(meuEmail));//quem recebe
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject(assunto);//vai ser o assunto da mensagem
            mensagem.setText(corpo);

            Transport.send(mensagem);//conecta no gmail e envia a mensagem
            System.out.println("E-mail enviado!");

        } catch (Exception e) {//essa tratativa pode ocorrer quando: senha invalida, servidor negou acesso ou o bloqueio de segurança
            System.out.println("Erro ao enviar e-mail:");
            e.printStackTrace();
        }
    }
}
