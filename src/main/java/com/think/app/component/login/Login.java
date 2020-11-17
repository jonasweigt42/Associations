package com.think.app.component.login;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.component.ViewUpdater;
import com.think.app.constants.HTMLConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.event.Publisher;
import com.think.app.event.UpdateLoginEvent;
import com.think.app.event.UpdateMainViewEvent;
import com.think.app.userinfo.UserInfo;
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
import com.vaadin.flow.component.notification.Notification;
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

	private Button loginButton = new Button();
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

	@Autowired
	private Logger logger;

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
		registrationButton.addClassName(HTMLConstants.REGISTRATION_BUTTON);
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
		User user = userService.getUserByMailAddress(mailAddress);
		if (user == null)
		{
			Notification.show(getTranslation("userNotRegistered"));
			loginForm.setError(true);
			return;
		}
		if (!encoder.matches(password, user.getPassword()))
		{
			loginForm.setError(true);
			return;
		}
		VaadinSession.getCurrent().setLocale(new Locale(user.getLanguage()));
		userInfo.setLoginData(true, user);
	}

	private void prepareLoginButton()
	{
		loginButton.addClickListener(e -> changeLoginState());
		loginButton.addClassName(HTMLConstants.HEADER_BUTTON);
		loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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
			loginButton.setText(getTranslation("logout"));
		} else
		{
			loginButton.setText(getTranslation("login"));
		}
	}

	public Button getLoginButton()
	{
		return loginButton;
	}

	public void updateUI()
	{
		updateUi();
		register.updateUi();
		forgetPasswordDialog.updateUi();
	}

	@EventListener
	public void onApplicationEvent(UpdateLoginEvent event)
	{
		logger.info("catched UpdateLoginEvent");
		close();
		updateButtonLabel();
		viewUpdater.updateViews();
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
