package com.think.app.userinfo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.think.app.entity.user.User;
import com.think.app.service.user.UserService;
import com.think.app.service.word.WordService;
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
	private List<String> wordsForAssociations;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private WordService wordService;
	
	public void login(String mailAddress, String password)
	{
		loggedInUser = callDbAndAuthenticateUser(mailAddress, password);
		loggedIn = loggedInUser != null;
		if (loggedIn)
		{
			sessionId = VaadinSession.getCurrent().getSession().getId();
			wordsForAssociations = wordService.getRandomWords(10);
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

	public List<String> getWordsForAssociations()
	{
		return wordsForAssociations;
	}

	public void setWordsForAssociations(List<String> wordsForAssociations)
	{
		this.wordsForAssociations = wordsForAssociations;
	}

}