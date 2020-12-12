package com.associations.app.component.login;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.associations.app.component.AssociationsNotification;
import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.user.User;
import com.associations.app.entity.user.UserService;
import com.associations.app.exception.ResetPasswordException;
import com.associations.app.service.MailService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.spring.annotation.UIScope;

import ch.qos.logback.classic.Logger;

@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Component
@UIScope
public class ForgetPasswordDialog extends Dialog implements LocaleChangeObserver
{

	private static final long serialVersionUID = 2214540533394292409L;

	private TextField mailAddress = new TextField();
	private Label errorLabel = new Label();
	private H2 title = new H2();
	private Button submit = new Button();
	private VerticalLayout layout = new VerticalLayout();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		layout.addClassName(CSSConstants.CENTERED_CONTENT);
		errorLabel.addClassName(CSSConstants.TEXT_RED);
		submit.addClickListener(ent -> validate(mailAddress.getValue()));
		
		updateUi();
	}

	public void updateUi()
	{
		removeAll();

		title.setText(getTranslation("assignNewPassword"));
		updateEMailField();
		
		submit.setText(getTranslation("reset"));

		setCloseOnEsc(true);
		setSizeFull();

		layout.add(title, mailAddress, errorLabel, submit);
		add(layout);
	}

	private void updateEMailField()
	{
		mailAddress.setLabel(getTranslation("email"));
		Binder<User> binder = new Binder<>();
		binder.forField(mailAddress)
				.withValidator(new EmailValidator(getTranslation("emailValidationErrorMessage")))
				.bind(User::getMailAddress, User::setMailAddress);
	}

	private void validate(String mailAddress)
	{
		User user = userService.getUserByMailAddress(mailAddress);

		if (user == null)
		{
			errorLabel.setText(getTranslation("userNotRegistered"));

		} else
		{
			String newPassword = generateRandomPassword();
			user.setPassword(encoder.encode(newPassword));
			userService.update(user);
			try
			{
				mailService.sendResetPasswordMail(mailAddress, user.getLanguage(), newPassword);
				AssociationsNotification.show(getTranslation("resetPasswordMail"));
			} catch (MessagingException | ResetPasswordException e)
			{
				logger.error(e.getMessage(), e);
				AssociationsNotification.showError(getTranslation("resetPasswordMailError"));
			}
			close();
		}
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		title.setText(getTranslation("assignNewPassword"));
		submit.setText(getTranslation("reset"));
		mailAddress.setLabel(getTranslation("email"));
	}

	private String generateRandomPassword()
	{
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String result = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    logger.info(result);
	    return result;
	}
	
}
