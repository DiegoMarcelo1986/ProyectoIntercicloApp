package testjpa.utils;

import java.util.Date;
import java.util.Properties;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.activation.DataHandler;

import javax.activation.FileDataSource;

import javax.mail.BodyPart;

import javax.mail.Message;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeBodyPart;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;

public class EnviarCorreoElectronico {

	// for example, smtp.mailgun.org
	private static final String SMTP_SERVER = "smtp.gmail.com";
	private static final String USERNAME = "parradiego396@gmail.com";
	private static final String PASSWORD = "JamesyJhuly2019";
	private static final String EMAIL_FROM = "parradiego396@gmail.com";
	private static final String EMAIL_TO_CC = "";

	final static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {

		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", SMTP_SERVER); // optional, defined in SMTPTransport
		prop.put("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
		prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		prop.setProperty("mail.smtp.socketFactory.fallback", "false");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.debug", "true");
		prop.put("mail.store.protocol", "pop3");
		prop.put("mail.transport.protocol", "smtp");

		Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

			// from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

			// to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario, false));

			// cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(EMAIL_TO_CC, false));

			// subject
            msg.setSubject(asunto);

			// content
            msg.setText(cuerpo);

            msg.setSentDate(new Date());

			// Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

			// connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

			// send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
