package com.think.app.component;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.think.app.constants.LanguageConstants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.spring.annotation.UIScope;

@CssImport(value = "./styles/dialog-styles.css")
@Component
@UIScope
public class ChangeLanguageButton extends Button
{

	private static final long serialVersionUID = 4142079310923863360L;

	@PostConstruct
	public void init()
	{
		setText(LanguageConstants.ENGLISH);
		addClickListener(evt -> toggleLanguage());
	}

	private void toggleLanguage()
	{
		if (getText().equals(LanguageConstants.ENGLISH))
		{
			setText(LanguageConstants.GERMAN);
			//TODO set language and throw update event
		} else
		{
			setText(LanguageConstants.ENGLISH);
			//TODO set language and throw update event
		}
	}
}
