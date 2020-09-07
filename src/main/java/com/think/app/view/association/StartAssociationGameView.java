package com.think.app.view.association;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.entity.user.User;
import com.think.app.textresources.TCResourceBundle;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "association", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class StartAssociationGameView extends VerticalLayout
{

	private static final long serialVersionUID = -6374720069983434221L;

	
	@Autowired
	private UserInfo userInfo;
	
	@Autowired 
	private TCResourceBundle tcResourceBundle;

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
			H4 label = new H4(tcResourceBundle.get(LanguageConstants.NOT_LOGGED_IN_MESSAGE));
			add(label);
		}
	}

	public void addFieldsForUser()
	{
		H4 label = new H4("Aufgabe: Du bekommst 10 zufällige Wörter, Bitte schreibe zu allen 3 Assoziationen auf");
		
		Button start = new Button("Start");
		start.addClickListener(evt -> navigate());

		add(label, start);
	}

	private void navigate()
	{
		UI.getCurrent().navigate(AssociationGameView.class);
	}

}
