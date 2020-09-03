package com.think.app.component;

import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.event.Publisher;
import com.think.app.event.UpdateLoginEvent;
import com.think.app.userinfo.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Component
@UIScope
public class Register extends Dialog
{

	private static final long serialVersionUID = -7750716192029688905L;

	private TextField firstName = new TextField();
	private TextField lastName = new TextField();
	private TextField mailAddress = new TextField();
	private PasswordField password = new PasswordField();
	private PasswordField passwordRetype = new PasswordField();
	private Label errorLabel = new Label();
	private Button submit = new Button(TextConstants.REGISTER);

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private Publisher publisher;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		VerticalLayout layout = new VerticalLayout();
		layout.addClassName(HTMLConstants.CENTERED_CONTENT);

		H4 title = new H4("Neu hier?");
		prepareFields();

		setCloseOnEsc(true);
		setSizeFull();

		layout.add(title, firstName, lastName, mailAddress, password, passwordRetype, errorLabel, submit);
		add(layout);

	}

	public void prepareFields()
	{
		mailAddress = prepareEMailField();
		errorLabel.addClassName("text-red");
		firstName.setLabel(TextConstants.FIRSTNAME);
		lastName.setLabel(TextConstants.LASTNAME);
		password.setLabel(TextConstants.PASSWORD);
		passwordRetype.setLabel(TextConstants.PASSWORD_RETYPE);
		submit.addClickListener(evt -> validateRegistration());
	}

	public TextField prepareEMailField()
	{
		mailAddress.setLabel(TextConstants.MAIL_ADDRESS);
		Binder<User> binder = new Binder<>();
		binder.forField(mailAddress).withValidator(new EmailValidator("bitte gib eine gültige E-Mail Adresse ein"))
				.bind(User::getMailAddress, User::setMailAddress);
		return mailAddress;
	}

	private void validateRegistration()
	{
		try
		{
			User user = userService.getUserByMailAddress(mailAddress.getValue());

			validateUser(user);
		} catch (InterruptedException | ExecutionException e)
		{
			errorLabel.setText(TextConstants.GENERIC_ERROR_MESSAGE);
			logger.error(e.getMessage(), e);
		}
	}

	private void validateUser(User user) throws InterruptedException, ExecutionException
	{
		if (user != null)
		{
			errorLabel.setText("Benutzer ist schon vorhanden");
		}
		if (user == null && !password.getValue().equals(passwordRetype.getValue()))
		{
			errorLabel.setText("Passwörter sind nicht gleich");
		}
		if (user == null && password.getValue().equals(passwordRetype.getValue()))
		{
			User newUser = createUser();
			userService.save(newUser);
			userInfo.login(newUser.getMailAddress(), passwordRetype.getValue());
			close();
			publisher.publishEvent(new UpdateLoginEvent(this));
		}
	}

	public User createUser()
	{
		String encodedPassword = encoder.encode(passwordRetype.getValue());
		User newUser = new User();
		newUser.setFirstName(firstName.getValue());
		newUser.setLastName(lastName.getValue());
		newUser.setMailAddress(mailAddress.getValue());
		newUser.setPassword(encodedPassword);
		
		return newUser;
	}

}
