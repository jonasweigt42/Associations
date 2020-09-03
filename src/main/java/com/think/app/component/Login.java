package com.think.app.component;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.think.app.constants.TextConstants;
import com.think.app.event.UpdateLoginEvent;
import com.think.app.userinfo.UserInfo;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.AbstractLogin.ForgotPasswordEvent;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
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
	private ForgetPasswordDialog forgotPasswordDialog;

	@Autowired
	private Register register;
	
	@Autowired
	private ViewUpdater viewUpdater;
	
	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		loginForm.setI18n(prepareI18n());
		prepareButtonLabel();
		prepareLoginButton();
		prepareLoginListener();
		prepareForgetPasswordListener();
		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);
		
		add(loginForm, prepareRegistrationButton());
	}
	
	@Override
	public void open()
	{
		if(!userInfo.isLoggedIn())
		{
			super.open();
		}
	}
	
	private Button prepareRegistrationButton()
	{
		Button button = new Button(TextConstants.REGISTER);
		button.addClassName("registration-button");
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
				forgotPasswordDialog.open();
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
				userInfo.login(event.getUsername(), event.getPassword());
				if (userInfo.isLoggedIn())
				{
					prepareButtonLabel();
					viewUpdater.updateViews();
					close();
				}
			}
		});
	}

	private void prepareLoginButton()
	{
		loginButton.addClickListener(e -> changeLoginState());
		loginButton.setClassName("login-button");
	}

	private LoginI18n prepareI18n()
	{
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.getForm().setTitle(TextConstants.TITLE);
		i18n.getForm().setUsername(TextConstants.MAIL_ADDRESS);
		i18n.getForm().setPassword(TextConstants.PASSWORD);
		i18n.getForm().setForgotPassword(TextConstants.FORGET_PASSWORD);
		return i18n;
	}

	private void changeLoginState()
	{
		if (userInfo.isLoggedIn())
		{
			userInfo.logout();
		} 
	}

	private void prepareButtonLabel()
	{
		if (userInfo.isLoggedIn())
		{
			loginButton.setText(TextConstants.LOGOUT);
		} else
		{
			loginButton.setText(TextConstants.LOGIN);
		}
	}

	public Button getLoginButton()
	{
		return loginButton;
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
