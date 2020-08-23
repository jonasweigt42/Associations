package com.think.app.view.association;

import java.sql.Date;
import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.TextConstants;
import com.think.app.entity.association.Association;
import com.think.app.entity.user.User;
import com.think.app.service.association.AssociationService;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.button.Button;
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
	private AssociationService associationService;
	
	private TextField ass1 = new TextField();
	private TextField ass2 = new TextField();
	private TextField ass3 = new TextField();
	private H4 label = new H4();
	
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
		if(userInfo.getWordsForAssociations().isEmpty())
		{
			return;
		}
		String word = userInfo.getWordsForAssociations().get(0);
		
		label.setText(word);

		Button save = new Button(TextConstants.SAVE);
		save.addClickListener(evt -> saveAndClear(word));

		add(label, ass1, ass2, ass3, save);
	}

	private void saveAndClear(String word)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(ass1.getValue()).append(",");
		builder.append(ass2.getValue()).append(",");
		builder.append(ass3.getValue());
		Association ass = new Association();
		ass.setUserId(userInfo.getLoggedInUser().getId());
		ass.setWord(word);
		ass.setAssociationDate(new Date(Calendar.getInstance().getTime().getTime()));
		ass.setAssociations(builder.toString());
		
		associationService.save(ass);
		
		userInfo.getWordsForAssociations().remove(word);
		if(userInfo.getWordsForAssociations().isEmpty())
		{
			remove(ass1, ass2, ass3);
			label.setText("Super! :)");
			add(label);
		}
		else
		{
			String nextWord = userInfo.getWordsForAssociations().get(0);
			label.setText(nextWord);
			ass1.clear();
			ass2.clear();
			ass3.clear();
		}
	}


}
