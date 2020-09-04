package com.think.app.textresources;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.think.app.constants.LanguageConstants;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@Component
@VaadinSessionScope
public class TCResourceBundle
{

	private Locale sessionLocale;
	
	@PostConstruct
	public void init()
	{
		sessionLocale = new Locale(LanguageConstants.ENGLISH);
	}
	
	public String get(String key)
	{
		ResourceBundle words = ResourceBundle.getBundle("text/text", sessionLocale);

		return words.getString(key);
	}
	
	public void setSessionLocale(Locale sessionLocale)
	{
		this.sessionLocale = sessionLocale;
	}
}
