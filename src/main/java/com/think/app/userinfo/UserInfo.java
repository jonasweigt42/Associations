package com.think.app.userinfo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.think.app.entity.user.User;
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

	public void loginAfterRegistration(User user)
	{
		loggedInUser = user;
		loggedIn = true;
		sessionId = VaadinSession.getCurrent().getSession().getId();
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

	public void setLoginData(boolean loggedIn, User loggedInUser)
	{
		this.loggedIn = loggedIn;
		this.loggedInUser = loggedInUser;
		sessionId = VaadinSession.getCurrent().getSession().getId();
	}
	
	public void invalidate()
	{
		sessionId = null;
		loggedIn = false;
		loggedInUser = null;
		VaadinSession.getCurrent().getSession().invalidate();
	}


}