package com.think.app.component.login;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.constants.CSSConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.userinfo.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Component
@UIScope
public class ChangePasswordDialog extends Dialog implements LocaleChangeObserver
{
	private static final long serialVersionUID = -4745741179152511046L;

	private PasswordField currentPassword = new PasswordField();
	private PasswordField newPassword = new PasswordField();
	private PasswordField newPasswordRetype = new PasswordField();
	private Label errorLabel = new Label();
	private H2 title = new H2();
	private Button submit = new Button();

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private PasswordEncoder encoder;

	@PostConstruct
	public void init()
	{
		loadUi();
	}

	private void loadUi()
	{
		removeAll();
		currentPassword.setLabel(getTranslation("currentPassword"));
		newPassword.setLabel(getTranslation("newPassword"));
		newPasswordRetype.setLabel(getTranslation("newPasswordRetype"));
		VerticalLayout layout = new VerticalLayout();
		layout.addClassName(CSSConstants.CENTERED_CONTENT);

		title.setText(getTranslation("assignNewPassword"));

		errorLabel.addClassName(CSSConstants.TEXT_RED);

		submit.addClickListener(ent -> validate());
		submit.setText(getTranslation("change"));

		setCloseOnEsc(true);

		layout.add(title, currentPassword, newPassword, newPasswordRetype, errorLabel, submit);
		add(layout);
	}

	private void validate()
	{
		User user = userInfo.getLoggedInUser();
		if (user == null)
		{
			throw new IllegalStateException(getTranslation(TextConstants.USER_SHOULD_BE_LOGGEDIN));
		}
		if (!currentPasswordMatches())
		{
			errorLabel.setText(getTranslation("currentPasswordIncorrect"));
		}
		if (currentPasswordMatches() && !newPasswordsMatches())
		{
			errorLabel.setText(getTranslation("passwordsNotEqual"));
		}
		if (currentPasswordMatches() && newPasswordsMatches())
		{
			user.setPassword(encoder.encode(newPasswordRetype.getValue()));

			userService.update(user);
			Notification.show(getTranslation("userWasUpdated"));
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

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		currentPassword.setLabel(getTranslation("currentPassword"));
		newPassword.setLabel(getTranslation("newPassword"));
		newPasswordRetype.setLabel(getTranslation("newPasswordRetype"));		
		title.setText(getTranslation("assignNewPassword"));
		submit.setText(getTranslation("change"));
	}
}
