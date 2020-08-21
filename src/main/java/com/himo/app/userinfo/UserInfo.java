package com.himo.app.userinfo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.himo.app.entity.user.User;
import com.himo.app.service.user.UserService;
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

	public void login(String mailAddress, String password)
	{
		loggedInUser = callDbAndAuthenticateUser(mailAddress, password);
		loggedIn = loggedInUser != null;
		if (loggedIn)
		{
			sessionId = VaadinSession.getCurrent().getSession().getId();
		}
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
