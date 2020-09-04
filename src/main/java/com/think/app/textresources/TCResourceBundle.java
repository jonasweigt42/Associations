package com.think.app.textresources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class TCResourceBundle
{

	public String getWord(Locale locale, String key)
	{
		ResourceBundle words = ResourceBundle.getBundle("text/text", locale);

		return words.getString(key);
	}
}
