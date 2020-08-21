package com.himo.app.view.association;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.himo.app.constants.HTMLConstants;
import com.himo.app.constants.TextConstants;
import com.himo.app.entity.user.User;
import com.himo.app.service.word.WordService;
import com.himo.app.userinfo.UserInfo;
import com.himo.app.view.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "game", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class AssociationGameView extends VerticalLayout
{

	private static final long serialVersionUID = 4153761837545371752L;

	@Autowired
	private UserInfo userInfo;
	
	@Autowired
	private WordService wordService;

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
			addFieldsForUser(user);
		} else
		{
			H4 label = new H4(TextConstants.NOT_LOGGED_IN_MESSAGE);
			add(label);
		}
	}

	public void addFieldsForUser(User user)
	{
		List<String> list = wordService.getRandomNames(10);
		
		list.forEach(e -> System.out.println(e));
		
		H4 label = new H4(list.get(0));

		TextField ass1 = new TextField();
		TextField ass2 = new TextField();
		TextField ass3 = new TextField();


		add(label, ass1, ass2, ass3);
	}


}
