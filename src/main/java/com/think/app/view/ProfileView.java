package com.think.app.view;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.component.ChangePasswordDialog;
import com.think.app.constants.HTMLConstants;
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
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "profile", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class ProfileView extends VerticalLayout implements LocaleChangeObserver
{

	private static final long serialVersionUID = -1468535064495134323L;

	private TextField firstname = new TextField();
	private TextField lastname = new TextField();
	private TextField mailaddress = new TextField();
	private Button changePasswordButton = new Button();
	private Button saveButton = new Button();

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
			prepareFirstnameTextField(user);
			prepareLastnameTextField(user);
			prepareMailadressTextField(user);
			prepareChangePasswordButton();
			prepareSaveButton();
			ComboBox<Locale> clCombobox = prepareChangeLanguageBox();

			add(firstname, lastname, mailaddress, saveButton, changePasswordButton, clCombobox);
		} else
		{
			H4 label = new H4(getTranslation("notLoggedInMessage"));
			add(label);
		}
	}

	private ComboBox<Locale> prepareChangeLanguageBox()
	{
		ComboBox<Locale> localeCombobox = new ComboBox<>();
		localeCombobox.setItems(Locale.ENGLISH, Locale.GERMAN);
		localeCombobox.setValue(VaadinSession.getCurrent().getLocale());
		localeCombobox.addValueChangeListener(event -> {
			VaadinSession.getCurrent().setLocale(event.getValue());
			User user = userInfo.getLoggedInUser();
			user.setLanguage(event.getValue().getLanguage());
			logger.info("changed language to {}", event.getValue().getLanguage());
			userService.update(user);
		});
		return localeCombobox;
	}

	private void prepareSaveButton()
	{
		saveButton.setText(getTranslation("save"));
		saveButton
				.addClickListener(evt -> updateUser(firstname.getValue(), lastname.getValue(), mailaddress.getValue()));
	}

	private void prepareChangePasswordButton()
	{
		changePasswordButton.setText(getTranslation("changePassword"));
		changePasswordButton.addClickListener(evt -> changePasswordDialog.open());
	}

	private void prepareMailadressTextField(User user)
	{
		mailaddress.setValue(user.getMailAddress());
		mailaddress.setLabel(getTranslation("email"));
	}

	private void prepareLastnameTextField(User user)
	{
		lastname.setValue(user.getLastName());
		lastname.setLabel(getTranslation("lastname"));
	}

	private void prepareFirstnameTextField(User user)
	{
		firstname.setValue(user.getFirstName());
		firstname.setLabel(getTranslation("firstname"));
	}

	private void updateUser(String firstname, String lastname, String username)
	{
		User user = userInfo.getLoggedInUser();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setMailAddress(username);

		userService.update(user);
		Notification.show(getTranslation("userWasUpdated"));
		logger.info("user was updated");
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		changePasswordButton.setText(getTranslation("changePassword"));
		saveButton.setText(getTranslation("save"));
		mailaddress.setLabel(getTranslation("email"));
		firstname.setLabel(getTranslation("firstname"));
		lastname.setLabel(getTranslation("lastname"));
	}

}
