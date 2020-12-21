package com.associations.app.service;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.associations.app.constants.TextConstants;
import com.associations.app.exception.MailException;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

@Service
public class MailService
{

	@Autowired
	private Logger logger;

	public void sendResetPasswordMail(String mailAddress, String language, String newPassword) throws MessagingException, MailException
	{
		Message msg = buildForgotPasswordMessage(mailAddress, language, newPassword);
		Transport.send(msg);
		logger.info("sent reset password mail");
	}
	
	public void sendVerificationMail(String mailAddress, String language, String token) throws MessagingException, MailException
	{
		Message msg = buildVerificationMessage(mailAddress, language, token);
		Transport.send(msg);
		logger.info("sent verification mail");
	}

	private Session prepareSession()
	{
		Properties props = prepareSystemProperties();
		final String username = "associations.info42@gmail.com";
		final String password = "Yolorolf142857.!";
		return Session.getInstance(props, new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});
	}
	
	private Message buildVerificationMessage(String mailAddress, String language, String token) throws MessagingException, MailException
	{
		Session session = prepareSession();
		Message msg = new MimeMessage(session);

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAddress, false));
		msg.setSubject(TextConstants.TITLE + " - " + prepareVerificationSubject(language));
		
		msg.setText(prepareVerificationText(language, token));
		msg.setSentDate(new Date());
		return msg;
	}

	private Message buildForgotPasswordMessage(String mailAddress, String language, String newPassword)
			throws MessagingException, MailException
	{
		Session session = prepareSession();
		Message msg = new MimeMessage(session);

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAddress, false));
		msg.setSubject(TextConstants.TITLE + " - " + prepareForgotPasswordSubject(language));

		msg.setText(prepareForgorPasswordText(language, newPassword));
		msg.setSentDate(new Date());
		return msg;
	}
	
	private String prepareVerificationText(String language, String token) throws MailException
	{
		VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
		HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
		String requestUrl = httpServletRequest.getRequestURL().toString();
		String verificationUrl = requestUrl + "confirmRegistration/" + token;
		
		if (Locale.GERMAN.toString().equals(language))
		{
			return "Bitte die Mail innerhalb 24 Stunden bestätigen unter: " + verificationUrl;
		}
		if (Locale.ENGLISH.toString().equals(language))
		{
			return "please verify mail within 24 hours here: " + verificationUrl;
		}
		throw new MailException(TextConstants.USER_LOCALE_NOT_AVAILABLE);
	}
	
	private String prepareForgorPasswordText(String language, String newPassword) throws MailException
	{
		if (Locale.GERMAN.toString().equals(language))
		{
			return "Passwort wurde auf '" + newPassword + "' zurückgesetzt.";
		}
		if (Locale.ENGLISH.toString().equals(language))
		{
			return "Password reset to '" + newPassword + "'.";
		}
		throw new MailException(TextConstants.USER_LOCALE_NOT_AVAILABLE);
	}

	private String prepareForgotPasswordSubject(String language) throws MailException
	{
		if (Locale.GERMAN.toString().equals(language))
		{
			return "Passwort zurückgesetzt";
		}
		if (Locale.ENGLISH.toString().equals(language))
		{
			return "reset password";
		}
		throw new MailException(TextConstants.USER_LOCALE_NOT_AVAILABLE);
	}
	
	private String prepareVerificationSubject(String language) throws MailException
	{
		if (Locale.GERMAN.toString().equals(language))
		{
			return "Mail Bestätigung";
		}
		if (Locale.ENGLISH.toString().equals(language))
		{
			return "Mail Verification";
		}
		throw new MailException(TextConstants.USER_LOCALE_NOT_AVAILABLE);
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
