package com.associations.app.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService
{
	
	@Autowired
	private Logger logger;

	public void sendResetPasswordMail(String mailAddress, String newPassword)
	{
		Properties props = prepareSystemProperties();
		final String username = "associations.info42@gmail.com";
		final String password = "Yolorolf142857.!";
		try
		{
			Session session = Session.getInstance(props, new Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(username, password);
				}
			});

			Message msg = new MimeMessage(session);

			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailAddress, false));
			msg.setSubject("Associations - Password zurückgesetzt");

			msg.setText("Password wurde auf '" + newPassword + "' zurückgesetzt");
			msg.setSentDate(new Date());
			Transport.send(msg);
			logger.info("Message sent.");
		} catch (MessagingException e)
		{
			logger.error(e.getMessage(), e);
		}

	}

	private Properties prepareSystemProperties()
	{
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		return props;
	}
}
