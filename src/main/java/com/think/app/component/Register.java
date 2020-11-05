package com.think.app.component;

import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
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
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Component
@UIScope
public class Register extends Dialog implements LocaleChangeObserver
{

	private static final long serialVersionUID = -7750716192029688905L;

	private H4 title = new H4();
	private TextField firstName = new TextField();
	private TextField lastName = new TextField();
	private TextField mailAddress = new TextField();
	private PasswordField password = new PasswordField();
	private PasswordField passwordRetype = new PasswordField();
	private Label errorLabel = new Label();
	private Button submit = new Button();
	private VerticalLayout layout = new VerticalLayout();

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
		layout.addClassName(HTMLConstants.CENTERED_CONTENT);
		errorLabel.addClassName(HTMLConstants.TEXT_RED);
		submit.addClickListener(evt -> validateRegistration());
		updateUi();
	}
	
	public void updateUi()
	{
		removeAll();

		title.setText(getTranslation("newRegister"));
		prepareFields();

		setCloseOnEsc(true);
		setSizeFull();

		layout.add(title, firstName, lastName, mailAddress, password, passwordRetype, errorLabel, submit);
		add(layout);
	}

	public void prepareFields()
	{
		prepareEMailField();
		firstName.setLabel(getTranslation("firstname"));
		lastName.setLabel(getTranslation("lastname"));
		password.setLabel(getTranslation("password"));
		passwordRetype.setLabel(getTranslation("retypePassword"));
		submit.setText(getTranslation("register"));
	}

	private void prepareEMailField()
	{
		mailAddress.setLabel(getTranslation("email"));
		Binder<User> binder = new Binder<>();
		binder.forField(mailAddress).withValidator(new EmailValidator(getTranslation("emailValidationErrorMessage")))
				.bind(User::getMailAddress, User::setMailAddress);
	}

	private void validateRegistration()
	{
		try
		{
			User user = userService.getUserByMailAddress(mailAddress.getValue());
			validateUser(user);
			
		} catch (InterruptedException | ExecutionException e)
		{
			errorLabel.setText(getTranslation("registrationErrorMessage"));
			logger.error(e.getMessage(), e);
		}
	}

	private void validateUser(User user) throws InterruptedException, ExecutionException
	{
		if (user != null)
		{
			errorLabel.setText(getTranslation("userAlreadyRegistered"));
		}
		if (user == null && !password.getValue().equals(passwordRetype.getValue()))
		{
			errorLabel.setText(getTranslation("passwordsNotEqual"));
		}
		if (user == null && password.getValue().equals(passwordRetype.getValue()))
		{
			User newUser = createUser();
			userService.save(newUser);
			userInfo.loginAfterRegistration(newUser);
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
		newUser.setLanguage(VaadinSession.getCurrent().getLocale().getLanguage());
		newUser.setPassword(encodedPassword);
		
		return newUser;
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		firstName.setLabel(getTranslation("firstname"));
		lastName.setLabel(getTranslation("lastname"));
		password.setLabel(getTranslation("password"));
		passwordRetype.setLabel(getTranslation("retypePassword"));
		submit.setText(getTranslation("register"));
		mailAddress.setLabel(getTranslation("email"));
	}

}
