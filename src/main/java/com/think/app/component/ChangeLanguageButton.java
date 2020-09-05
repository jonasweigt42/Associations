package com.think.app.component;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.LanguageConstants;
import com.think.app.textresources.TCResourceBundle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport(value = "./styles/dialog-styles.css")
@Component
@UIScope
public class ChangeLanguageButton extends Button
{

	private static final long serialVersionUID = 4142079310923863360L;

	@Autowired
	private TCResourceBundle tcResourceBundle;

	@PostConstruct
	public void init()
	{
		setText(tcResourceBundle.getSessionLocale().getLanguage());
	}

	public void toggle()
	{
		if (getText().equals(LanguageConstants.ENGLISH))
		{
			setText(LanguageConstants.GERMAN);
			tcResourceBundle.setSessionLocale(new Locale(LanguageConstants.GERMAN));
		} else
		{
			setText(LanguageConstants.ENGLISH);
			tcResourceBundle.setSessionLocale(new Locale(LanguageConstants.ENGLISH));
		}
	}

}
