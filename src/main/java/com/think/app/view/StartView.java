package com.think.app.view;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.entity.user.User;
import com.think.app.service.user.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
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

	TextField firstName = new TextField();
	TextField lastName = new TextField();
	TextField mailAddress = new TextField();
	PasswordField passWord = new PasswordField();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		addClassName(HTMLConstants.CENTERED_CONTENT);

		loadContent();
	}

	public void loadContent()
	{
		removeAll();
		
		Button save = new Button("Speichern");
//		H3 headline = new H3("Herzlich Willkommen bei " + TextConstants.TITLE);
//		H4 personalLabel = preparePersonalLabel();

		save.addClickListener(evt -> saveUser());
		
		add(firstName, lastName, mailAddress, passWord, save);
	}

	private void saveUser()
	{
		User user = new User();
		user.setFirstName(firstName.getValue());
		user.setLastName(lastName.getValue());
		user.setMailAddress(mailAddress.getValue());
		user.setPassword(passWord.getValue());
	
		try
		{
			String time = userService.saveUser(user);
			logger.info("user saved : " + time);
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		
	}

	private H4 preparePersonalLabel()
	{
		H4 label = new H4("Test");
//		User user = userInfo.getLoggedInUser();
//		if (user != null)
//		{
//			label.setText("Hi " + user.getFirstName()
//					+ "! Mit nur 2 Minuten Übung am Tag kannst du dein assoziatives Denken und dein Gedächtnis verbessern, sowie deinen Wortschatz erweitern.");
//		} else
//		{
//			label.setText(TextConstants.NOT_LOGGED_IN_MESSAGE);
//		}
		return label;
	}

}
