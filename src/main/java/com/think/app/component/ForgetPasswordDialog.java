package com.think.app.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.textresources.TCResourceBundle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Component
@UIScope
public class ForgetPasswordDialog extends Dialog
{

	private static final long serialVersionUID = 2214540533394292409L;

	private TextField mailAddress = new TextField();
	private Label errorLabel = new Label();

	@Autowired
	private UserService userService;
	
	@Autowired
	private TCResourceBundle tcResourceBundle;

	@PostConstruct
	public void init()
	{
		VerticalLayout layout = new VerticalLayout();
		layout.addClassName(HTMLConstants.CENTERED_CONTENT);

		H2 title = new H2(tcResourceBundle.get(LanguageConstants.ASSIGN_NEW_PASSWORD));
		mailAddress = prepareEMailField();

		errorLabel.addClassName(HTMLConstants.TEXT_RED);

		Button submit = new Button();
		submit.addClickListener(ent -> validate(mailAddress.getValue()));
		submit.setText(tcResourceBundle.get(LanguageConstants.RESET));

		setCloseOnEsc(true);
		setSizeFull();

		layout.add(title, mailAddress, errorLabel, submit);
		add(layout);

	}

	public TextField prepareEMailField()
	{
		mailAddress.setLabel(tcResourceBundle.get(LanguageConstants.MAIL_ADDRESS));
		Binder<User> binder = new Binder<>();
		binder.forField(mailAddress).withValidator(new EmailValidator(tcResourceBundle.get(LanguageConstants.PLEASE_ENTER_VALID_MAIL)))
				.bind(User::getMailAddress, User::setMailAddress);
		return mailAddress;
	}

	private void validate(String mailAddress)
	{
//		User user = userService.getUserByMailAddress(mailAddress);
//
//		if (user == null)
//		{
//			errorLabel.setText("Benutzer ist nicht registriert");
//
//		} else
//		{
//			 TODO add send mail and generate random password
//			user.setPassword(encoder.encode(randomPassword));
//			userService.update(user);
//			Notification.show("Passwort für " + user.getMailAddress() + " zurückgesetzt");
//			Notification.show("not implemented yet");
//			close();
//		}
	}

}
