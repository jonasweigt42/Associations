package com.think.app.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.user.User;
import com.think.app.textresources.TCResourceBundle;
import com.think.app.userinfo.UserInfo;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;

@RouteAlias(value = "", layout = MainView.class)
@Route(value = "start", layout = MainView.class)
@PageTitle("Start")
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class StartView extends VerticalLayout
{

	private static final long serialVersionUID = 1686035666342372757L;

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
		H3 headline = new H3(tcResourceBundle.get(LanguageConstants.WELCOME) + TextConstants.TITLE);
		H4 personalLabel = preparePersonalLabel();

		add(headline, personalLabel);
	}

	private H4 preparePersonalLabel()
	{
		H4 label = new H4();
		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			label.setText(tcResourceBundle.get(LanguageConstants.HI) + user.getFirstName()
					+ tcResourceBundle.get(LanguageConstants.WELCOME_TEXT));
		} else
		{
			label.setText(tcResourceBundle.get(LanguageConstants.NOT_LOGGED_IN_MESSAGE));
		}
		return label;
	}

}
