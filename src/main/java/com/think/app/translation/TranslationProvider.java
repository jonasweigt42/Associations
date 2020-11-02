package com.think.app.translation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.vaadin.flow.i18n.I18NProvider;

@Component
public class TranslationProvider implements I18NProvider
{

	private static final long serialVersionUID = -8398798140848645652L;

	@Override
	public List<Locale> getProvidedLocales()
	{
		return Collections.unmodifiableList(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
	}

	@Override
	public String getTranslation(String key, Locale locale, Object... params)
	{
		ResourceBundle words = ResourceBundle.getBundle("text/text", locale);

		String value = words.getString(key);
		if(params.length > 0)
		{
			value = MessageFormat.format(value, params);
		}
		return value;
	}

}
