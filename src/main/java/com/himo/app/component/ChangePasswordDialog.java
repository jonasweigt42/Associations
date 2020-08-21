package com.himo.app.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.himo.app.constants.HTMLConstants;
import com.himo.app.constants.TextConstants;
import com.himo.app.entity.user.User;
import com.himo.app.service.user.UserService;
import com.himo.app.userinfo.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Component
@UIScope
public class ChangePasswordDialog extends Dialog
{
	private static final long serialVersionUID = -4745741179152511046L;

	private PasswordField currentPassword = new PasswordField(TextConstants.CURRENT_PASSWORD);
	private PasswordField newPassword = new PasswordField(TextConstants.NEW_PASSWORD);
	private PasswordField newPasswordRetype = new PasswordField(TextConstants.NEW_PASSWORD_RETYPE);
	private Label errorLabel = new Label();

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private PasswordEncoder encoder;

	@PostConstruct
	public void init()
	{
		VerticalLayout layout = new VerticalLayout();
		layout.addClassName(HTMLConstants.CENTERED_CONTENT);

		H2 title = new H2(TextConstants.ASSIGN_NEW_PASSWORD);

		errorLabel.addClassName(HTMLConstants.TEXT_RED);

		Button submit = new Button();
		submit.addClickListener(ent -> validate());
		submit.setText(TextConstants.CHANGE);

		setCloseOnEsc(true);
		setSizeFull();

		layout.add(title, currentPassword, newPassword, newPasswordRetype, errorLabel, submit);
		add(layout);

	}

	private void validate()
	{
		User user = userInfo.getLoggedInUser();
		if (user == null)
		{
			throw new IllegalStateException("user should be logged in");
		}
		if (!currentPasswordMatches())
		{
			errorLabel.setText("aktuelles Passwort ist nicht korrekt!");
		}
		if (currentPasswordMatches() && !newPasswordsMatches())
		{
			errorLabel.setText("Passwörter sind nicht gleich");
		}
		if (currentPasswordMatches() && newPasswordsMatches())
		{
			user.setPassword(encoder.encode(newPasswordRetype.getValue()));
			userService.update(user);
			Notification.show("Passwort für " + user.getMailAddress() + " geändert");
			close();
			clearAll();
		}
	}

	private boolean currentPasswordMatches()
	{
		User user = userInfo.getLoggedInUser();
		return encoder.matches(currentPassword.getValue(), user.getPassword());
	}

	private boolean newPasswordsMatches()
	{
		return newPassword.getValue().equals(newPasswordRetype.getValue());
	}

	private void clearAll()
	{
		currentPassword.clear();
		newPassword.clear();
		newPasswordRetype.clear();
		errorLabel.setText("");
	}
}
