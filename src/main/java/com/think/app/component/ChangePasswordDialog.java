package com.think.app.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.textresources.TCResourceBundle;
import com.think.app.userinfo.UserInfo;
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

	private PasswordField currentPassword = new PasswordField();
	private PasswordField newPassword = new PasswordField();
	private PasswordField newPasswordRetype = new PasswordField();
	private Label errorLabel = new Label();

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private TCResourceBundle tcResourceBundle;

	@PostConstruct
	public void init()
	{
		loadContent();
	}

	public void loadContent()
	{
		removeAll();
		currentPassword.setLabel(tcResourceBundle.get(LanguageConstants.CURRENT_PASSWORD));
		newPassword.setLabel(tcResourceBundle.get(LanguageConstants.NEW_PASSWORD));
		newPasswordRetype.setLabel(tcResourceBundle.get(LanguageConstants.NEW_PASSWORD_RETYPE));
		VerticalLayout layout = new VerticalLayout();
		layout.addClassName(HTMLConstants.CENTERED_CONTENT);

		H2 title = new H2(tcResourceBundle.get(LanguageConstants.ASSIGN_NEW_PASSWORD));

		errorLabel.addClassName(HTMLConstants.TEXT_RED);

		Button submit = new Button();
		submit.addClickListener(ent -> validate());
		submit.setText(tcResourceBundle.get(LanguageConstants.CHANGE));

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
			throw new IllegalStateException(tcResourceBundle.get(TextConstants.USER_SHOULD_BE_LOGGEDIN));
		}
		if (!currentPasswordMatches())
		{
			errorLabel.setText(tcResourceBundle.get(LanguageConstants.CURRENT_PASSWORD_INCORRECT));
		}
		if (currentPasswordMatches() && !newPasswordsMatches())
		{
			errorLabel.setText(tcResourceBundle.get(LanguageConstants.PASSWORDS_NOT_EQUAL));
		}
		if (currentPasswordMatches() && newPasswordsMatches())
		{
			user.setPassword(encoder.encode(newPasswordRetype.getValue()));

			userService.update(user);
			Notification.show(tcResourceBundle.get(LanguageConstants.USER_WAS_UPDATED));
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
