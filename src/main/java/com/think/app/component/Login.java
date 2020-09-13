package com.think.app.component;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.event.UpdateLoginEvent;
import com.think.app.textresources.TCResourceBundle;
import com.think.app.userinfo.UserInfo;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.AbstractLogin.ForgotPasswordEvent;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginI18n.ErrorMessage;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport(value = "./styles/dialog-styles.css", themeFor = "vaadin-dialog-overlay")
@Component
@UIScope
public class Login extends Dialog implements ApplicationListener<UpdateLoginEvent>
{

	private static final long serialVersionUID = -3124840772943883433L;

	private Button loginButton = new Button();
	private LoginForm loginForm = new LoginForm();

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private ForgetPasswordDialog forgetPasswordDialog;

	@Autowired
	private Register register;

	@Autowired
	private ViewUpdater viewUpdater;

	@Autowired
	private TCResourceBundle tcResourceBundle;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		loadContent();
	}

	public void loadContent()
	{
		removeAll();
		prepareI18n();
		loginForm.setI18n(prepareI18n());
		prepareButtonLabel();
		prepareLoginButton();
		prepareLoginListener();
		prepareForgetPasswordListener();
		setCloseOnEsc(false);

		add(loginForm, prepareRegistrationButton());
	}

	@Override
	public void open()
	{
		if (!userInfo.isLoggedIn())
		{
			super.open();
		}
	}

	private Button prepareRegistrationButton()
	{
		Button button = new Button(tcResourceBundle.get(LanguageConstants.REGISTER));
		button.addClassName(HTMLConstants.REGISTRATION_BUTTON);
		button.addClickListener(evt -> register.open());
		return button;
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
					prepareButtonLabel();
					viewUpdater.updateViews();
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
			Notification.show(tcResourceBundle.get(LanguageConstants.USER_NOT_REGISTERED));			
			loginForm.setError(true);
			return;
		}
		if (!encoder.matches(password, user.getPassword()))
		{
			loginForm.setError(true);
			return;
		}
		tcResourceBundle.setSessionLocale(new Locale(user.getLanguage()));
		userInfo.setLoginData(true, user);
	}
	
	private void prepareLoginButton()
	{
		loginButton.addClickListener(e -> changeLoginState());
		loginButton.addClassName(HTMLConstants.HEADER_BUTTON);
	}

	private LoginI18n prepareI18n()
	{
		LoginI18n i18n = LoginI18n.createDefault();
		ErrorMessage errorMessage = prepareErrorMessageI18n();
		i18n.setErrorMessage(errorMessage);
		i18n.getForm().setTitle(TextConstants.TITLE);
		i18n.getForm().setUsername(tcResourceBundle.get(LanguageConstants.MAIL_ADDRESS));
		i18n.getForm().setPassword(tcResourceBundle.get(LanguageConstants.PASSWORD));
		i18n.getForm().setForgotPassword(tcResourceBundle.get(LanguageConstants.FORGET_PASSWORD));
		return i18n;
	}

	private ErrorMessage prepareErrorMessageI18n()
	{
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(tcResourceBundle.get(LanguageConstants.LOGIN_ERROR_MESSAGE));
		errorMessage.setTitle(tcResourceBundle.get(LanguageConstants.LOGIN_ERROR_TITLE));
		return errorMessage;
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

	private void prepareButtonLabel()
	{
		if (userInfo.isLoggedIn())
		{
			loginButton.setText(tcResourceBundle.get(LanguageConstants.LOGOUT));
		} else
		{
			loginButton.setText(tcResourceBundle.get(LanguageConstants.LOGIN));
		}
	}

	public Button getLoginButton()
	{
		return loginButton;
	}
	
	public void updateUI()
	{
		loadContent();
		register.loadContent();
		forgetPasswordDialog.loadContent();
	}

	@Override
	public void onApplicationEvent(UpdateLoginEvent event)
	{
		logger.info("catched UpdateLoginEvent");
		close();
		prepareButtonLabel();
		viewUpdater.updateViews();
	}

}
