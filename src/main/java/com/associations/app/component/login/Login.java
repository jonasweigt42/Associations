package com.associations.app.component.login;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.associations.app.component.AssociationsNotification;
import com.associations.app.component.ViewUpdater;
import com.associations.app.constants.CSSConstants;
import com.associations.app.constants.ComponentIdConstants;
import com.associations.app.constants.TextConstants;
import com.associations.app.entity.user.User;
import com.associations.app.entity.user.UserService;
import com.associations.app.event.Publisher;
import com.associations.app.event.UpdateMainViewEvent;
import com.associations.app.userinfo.UserInfo;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.AbstractLogin.ForgotPasswordEvent;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginI18n.ErrorMessage;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport(value = "./styles/dialog-styles.css", themeFor = "vaadin-dialog-overlay")
@Component
@UIScope
public class Login extends Dialog implements LocaleChangeObserver
{

	private static final long serialVersionUID = -3124840772943883433L;

	private Button mainViewloginButton = new Button();
	private Button registrationButton = new Button();
	private LoginForm loginForm = new LoginForm();
	private LoginI18n i18n = LoginI18n.createDefault();
	private ErrorMessage errorMessage = new ErrorMessage();

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private ForgetPasswordDialog forgetPasswordDialog;

	@Autowired
	private Register register;

	@Autowired
	private ViewUpdater viewUpdater;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private Publisher publisher;

	@PostConstruct
	public void init()
	{
		prepareLoginListener();
		prepareLoginButton();
		prepareForgetPasswordListener();
		prepareRegistrationButton();
		updateUi();
	}

	public void updateUi()
	{
		removeAll();
		updateI18n();
		loginForm.setI18n(i18n);
		updateButtonLabel();
		registrationButton.setText(getTranslation("register"));
		setCloseOnEsc(false);
		add(loginForm, registrationButton);
	}

	@Override
	public void open()
	{
		if (!userInfo.isLoggedIn())
		{
			super.open();
		}
	}

	private void prepareRegistrationButton()
	{
		registrationButton.addClassName(CSSConstants.REGISTRATION_BUTTON);
		registrationButton.addClickListener(evt -> register.open());
	}

	private void prepareForgetPasswordListener()
	{
		loginForm.addForgotPasswordListener(new ComponentEventListener<ForgotPasswordEvent>()
		{

			private static final long serialVersionUID = -6677798405282464643L;

			@Override
			public void onComponentEvent(ForgotPasswordEvent event)
			{
				forgetPasswordDialog.open();
			}
		});
	}

	private void prepareLoginListener()
	{
		loginForm.addLoginListener(new ComponentEventListener<LoginEvent>()
		{
			private static final long serialVersionUID = -50433215575229805L;

			@Override
			public void onComponentEvent(LoginEvent event)
			{
				login(event.getUsername(), event.getPassword());
				if (userInfo.isLoggedIn())
				{
					updateButtonLabel();
					viewUpdater.updateViews();
					publisher.publishEvent(new UpdateMainViewEvent(this));
					close();
				} else
				{
					loginForm.setError(true);
				}
			}
		});
	}

	private void login(String mailAddress, String password)
	{
		User user = userService.findByMailAddress(mailAddress);
		if (user == null)
		{
			AssociationsNotification.showError(getTranslation("userNotRegistered"));
			loginForm.setError(true);
			return;
		}
		if (!encoder.matches(password, user.getPassword()))
		{
			loginForm.setError(true);
			return;
		}
		if(!user.isEnabled())
		{
			AssociationsNotification.showError(getTranslation("userNotVerified"));
			loginForm.setError(true);
			return;
		}
		VaadinSession.getCurrent().setLocale(new Locale(user.getLanguage()));
		userInfo.setLoginData(true, user);
	}

	private void prepareLoginButton()
	{
		mainViewloginButton.addClickListener(e -> changeLoginState());
		mainViewloginButton.addClassName(CSSConstants.HEADER_BUTTON);
		mainViewloginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		mainViewloginButton.setId(ComponentIdConstants.LOGIN_BUTTON);
	}

	private void updateI18n()
	{
		updateErrorMessageI18n();
		i18n.setErrorMessage(errorMessage);
		i18n.getForm().setTitle(TextConstants.TITLE);
		i18n.getForm().setUsername(getTranslation("email"));
		i18n.getForm().setPassword(getTranslation("password"));
		i18n.getForm().setForgotPassword(getTranslation("forgotPassword"));
	}

	private void updateErrorMessageI18n()
	{
		errorMessage.setMessage(getTranslation("loginErrorMessage"));
		errorMessage.setTitle(getTranslation("loginErrorTitle"));
	}

	private void changeLoginState()
	{
		if (userInfo.isLoggedIn())
		{
			logout();
		} else
		{
			open();
		}
	}

	private void logout()
	{
		userInfo.invalidate();
	}

	private void updateButtonLabel()
	{
		if (userInfo.isLoggedIn())
		{
			mainViewloginButton.setText(getTranslation("logout"));
		} else
		{
			mainViewloginButton.setText(getTranslation("login"));
		}
	}

	public Button getMainViewLoginButton()
	{
		return mainViewloginButton;
	}

	public void updateAllLoginUis()
	{
		updateUi();
		register.updateUi();
		forgetPasswordDialog.updateUi();
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		registrationButton.setText(getTranslation("register"));
		i18n.getForm().setUsername(getTranslation("email"));
		i18n.getForm().setPassword(getTranslation("password"));
		i18n.getForm().setForgotPassword(getTranslation("forgotPassword"));
		updateErrorMessageI18n();
		updateButtonLabel();
	}

}
