package com.think.app.view.association;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.entity.association.Association;
import com.think.app.entity.association.AssociationService;
import com.think.app.entity.user.User;
import com.think.app.entity.word.WordService;
import com.think.app.textresources.TCResourceBundle;
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

	private TextField associationField1 = new TextField();
	private TextField associationField2 = new TextField();
	private TextField associationField3 = new TextField();
	private H4 label = new H4();

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private TCResourceBundle tcResourceBundle;

	@Autowired
	private WordService wordService;

	@Autowired
	private AssociationService associationService;

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
			label = new H4(tcResourceBundle.get(LanguageConstants.NOT_LOGGED_IN_MESSAGE));
			add(label);
		}
	}

	public void addFieldsForUser(User user)
	{
		List<String> words = wordService.getRandomWords(10, user.getLanguage());
		if(words.isEmpty())
		{
			return;
		}
		String word = words.get(0);

		label.setText(word);

		Button save = new Button(tcResourceBundle.get(LanguageConstants.SAVE));
		save.addClickListener(evt -> saveAndClear(words));

		add(label, associationField1, associationField2, associationField3, save);
	}

	private void saveAndClear(List<String> words)
	{
		String word = label.getText();
		saveNewAssociationEntity(word);

		words.remove(word);
		if (words.isEmpty())
		{
			removeAll();
			label.setText(tcResourceBundle.get(LanguageConstants.FINISHED_EXERCISE) + " "
					+ tcResourceBundle.get(LanguageConstants.STATISTICS_MAIN_VIEW));
			add(label);
		} else
		{
			String nextWord = words.get(0);
			label.setText(nextWord);
			clearAssociationFields();
		}
	}

	private void saveNewAssociationEntity(String word)
	{
		Association associationEntity = new Association();
		associationEntity.setUserId(userInfo.getLoggedInUser().getId());
		associationEntity.setWord(word);
		associationEntity.setAssociationDate(new Date(Calendar.getInstance().getTime().getTime()));
		associationEntity.setAssociation1(associationField1.getValue());
		associationEntity.setAssociation2(associationField2.getValue());
		associationEntity.setAssociation3(associationField3.getValue());
		associationService.save(associationEntity);
	}

	private void clearAssociationFields()
	{
		associationField1.clear();
		associationField2.clear();
		associationField3.clear();
	}

}
