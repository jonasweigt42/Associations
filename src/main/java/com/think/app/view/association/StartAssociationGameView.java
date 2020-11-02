package com.think.app.view.association;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.entity.user.User;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "association", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class StartAssociationGameView extends VerticalLayout implements LocaleChangeObserver
{

	private static final long serialVersionUID = -6374720069983434221L;

	private H4 notLoggedInLabel = new H4();
	private H4 startExerciseLabel = new H4();
	private Button startButton = new Button();

	@Autowired
	private UserInfo userInfo;

	@PostConstruct
	public void init()
	{
		addClassName(HTMLConstants.CENTERED_CONTENT);

		loadContent();
	}

	public void loadContent()
	{
		removeAll();

		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			addFieldsForUser();
		} else
		{
			notLoggedInLabel.setText(getTranslation(LanguageConstants.NOT_LOGGED_IN_MESSAGE));
			add(notLoggedInLabel);
		}
	}

	public void addFieldsForUser()
	{
		startExerciseLabel.setText(getTranslation(LanguageConstants.EXERCISE));

		startButton.setText(getTranslation(LanguageConstants.START));
		startButton.addClickListener(evt -> navigate());

		add(startExerciseLabel, startButton);
	}

	private void navigate()
	{
		UI.getCurrent().navigate(AssociationGameView.class);
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		notLoggedInLabel.setText(getTranslation(LanguageConstants.NOT_LOGGED_IN_MESSAGE));
		startExerciseLabel.setText(getTranslation(LanguageConstants.EXERCISE));
		startButton.setText(getTranslation(LanguageConstants.START));
	}

}
