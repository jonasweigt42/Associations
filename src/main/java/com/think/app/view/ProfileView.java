package com.think.app.view;

import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.component.ChangePasswordDialog;
import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.userinfo.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "profile", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class ProfileView extends VerticalLayout
{

	private static final long serialVersionUID = -1468535064495134323L;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private UserService userService;

	@Autowired
	private ChangePasswordDialog changePasswordDialog;

	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		addClassName(HTMLConstants.CENTERED_CONTENT);

		loadContent();
	}

	public void loadContent()
	{
		removeAll();
		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			TextField firstname = prepareFirstnameTextField(user);
			TextField lastname = prepareLastnameTextField(user);
			TextField mailaddress = prepareMailadressTextField(user);

			Button changePassword = new Button(TextConstants.CHANGE_PASSWORD);
			changePassword.addClickListener(evt -> changePasswordDialog.open());

			Button save = new Button(TextConstants.SAVE);
			save.addClickListener(evt -> updateUser(firstname.getValue(), lastname.getValue(), mailaddress.getValue()));

			ComboBox<String> languages = prepareLanguagesComboBox(user.getLanguage());

			add(firstname, lastname, mailaddress, languages, save, changePassword);
		} else
		{
			H4 label = new H4(TextConstants.NOT_LOGGED_IN_MESSAGE);
			add(label);
		}
	}

	private ComboBox<String> prepareLanguagesComboBox(String defaultLanguage)
	{
		ComboBox<String> languages = new ComboBox<String>();
		languages.setLabel(TextConstants.CHANGE_LANGUAGE);
		languages.setItems(new String[]
		{ LanguageConstants.ENGLISH, LanguageConstants.GERMAN });
		languages.setValue(defaultLanguage);
		return languages;
	}

	public TextField prepareMailadressTextField(User user)
	{
		TextField mailaddress = new TextField();
		mailaddress.setValue(user.getMailAddress());
		mailaddress.setLabel(TextConstants.MAIL_ADDRESS);
		return mailaddress;
	}

	public TextField prepareLastnameTextField(User user)
	{
		TextField lastname = new TextField();
		lastname.setValue(user.getLastName());
		lastname.setLabel(TextConstants.LASTNAME);
		return lastname;
	}

	public TextField prepareFirstnameTextField(User user)
	{
		TextField firstname = new TextField();
		firstname.setValue(user.getFirstName());
		firstname.setLabel(TextConstants.FIRSTNAME);
		return firstname;
	}

	private void updateUser(String firstname, String lastname, String username)
	{
		User user = userInfo.getLoggedInUser();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setMailAddress(username);
		try
		{
			userService.update(user);
		} catch (InterruptedException | ExecutionException e)
		{
			logger.error(e.getMessage(), e);
			Notification.show(TextConstants.GENERIC_ERROR_MESSAGE);
		}
		Notification.show(TextConstants.USER_WAS_UPDATED);
		logger.info(TextConstants.USER_WAS_UPDATED);
	}

}
