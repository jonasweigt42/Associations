package com.think.app.userinfo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.constants.LanguageConstants;
import com.think.app.entity.user.User;
import com.think.app.entity.user.UserService;
import com.think.app.textresources.TCResourceBundle;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@Component
@VaadinSessionScope
public class UserInfo implements Serializable
{
	private static final long serialVersionUID = -4554390157557008979L;

	private String sessionId;
	private boolean loggedIn;
	private User loggedInUser;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private TCResourceBundle tcResourceBundle;

	public void login(String mailAddress, String password)
	{
		loggedInUser = callDbAndAuthenticateUser(mailAddress, password);
		loggedIn = loggedInUser != null;
		if (loggedIn)
		{
			sessionId = VaadinSession.getCurrent().getSession().getId();
		}
	}

	public void loginAfterRegistration(User user)
	{
		loggedInUser = user;
		loggedIn = true;
		sessionId = VaadinSession.getCurrent().getSession().getId();
	}

	public void logout()
	{
		sessionId = null;
		loggedIn = false;
		loggedInUser = null;
		VaadinSession.getCurrent().getSession().invalidate();
	}

	private User callDbAndAuthenticateUser(String mailAddress, String password)
	{

		User user = userService.getUserByMailAddress(mailAddress);
		if (user == null)
		{
			Notification.show(tcResourceBundle.get(LanguageConstants.USER_NOT_REGISTERED));
			return null;
		}
		if (encoder.matches(password, user.getPassword()))
		{
			return user;
		}
		return null;

	}

	public String getSessionId()
	{
		return sessionId;
	}

	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	public User getLoggedInUser()
	{
		return loggedInUser;
	}

}