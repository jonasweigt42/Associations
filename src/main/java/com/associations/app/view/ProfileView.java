package com.associations.app.view;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.component.ChangeLanguageComponent;
import com.associations.app.component.login.ChangePasswordDialog;
import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.user.User;
import com.associations.app.entity.user.UserService;
import com.associations.app.userinfo.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
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
	private H4 notLoggedInLabel = new H4();

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private UserService userService;

	@Autowired
	private ChangePasswordDialog changePasswordDialog;
	
	@Autowired
	private ChangeLanguageComponent clComponent;

	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		addClassName(CSSConstants.CENTERED_CONTENT);
		
		changePasswordButton.addClickListener(evt -> changePasswordDialog.open());
		changePasswordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton
				.addClickListener(evt -> updateUser(firstname.getValue(), lastname.getValue(), mailaddress.getValue()));
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		updateUi();
	}

	public void updateUi()
	{
		removeAll();
		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			updateFirstnameTextField(user);
			updateLastnameTextField(user);
			updateMailadressTextField(user);
			updateChangePasswordButton();
			updateSaveButton();
			FlexLayout layout = new FlexLayout();
			layout.add(clComponent.getIcon(), clComponent.getClCombobox());
			
			add(firstname, lastname, mailaddress, saveButton, changePasswordButton, layout);
		} else
		{
			notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
			add(notLoggedInLabel);
		}
	}
	
	private void updateSaveButton()
	{
		saveButton.setText(getTranslation("save"));
	}

	private void updateChangePasswordButton()
	{
		changePasswordButton.setText(getTranslation("changePassword"));
	}

	private void updateMailadressTextField(User user)
	{
		mailaddress.setValue(user.getMailAddress());
		mailaddress.setLabel(getTranslation("email"));
	}

	private void updateLastnameTextField(User user)
	{
		lastname.setValue(user.getLastName());
		lastname.setLabel(getTranslation("lastname"));
	}

	private void updateFirstnameTextField(User user)
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
		notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
	}

}
