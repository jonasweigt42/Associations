package com.associations.app.userinfo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.associations.app.entity.user.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@Component
@VaadinSessionScope
public class UserInfo implements Serializable
{
	private static final long serialVersionUID = -4554390157557008979L;

	private boolean loggedIn;
	private User loggedInUser;
	
	public void loginAfterRegistration(User user)
	{
		loggedInUser = user;
		loggedIn = true;
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
	}
	
	public void invalidate()
	{
		UI.getCurrent().getPage().executeJs("location.assign('')");
		loggedIn = false;
		loggedInUser = null;
		VaadinSession.getCurrent().getSession().invalidate();
	}


}