package com.think.app.view.association;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.entity.user.User;
import com.think.app.view.MainView;
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

	@PostConstruct
	public void init()
	{
		addClassName(HTMLConstants.CENTERED_CONTENT);

		loadContent();
	}

	public void loadContent()
	{
		removeAll();

//		User user = userInfo.getLoggedInUser();
//		if (user != null)
//		{
//			addFieldsForUser(user);
//		} else
//		{
//			H4 label = new H4(TextConstants.NOT_LOGGED_IN_MESSAGE);
//			add(label);
//		}
	}

	public void addFieldsForUser(User user)
	{
//		if (userInfo.getWordsForAssociations().isEmpty())
//		{
//			return;
//		}
//		String word = userInfo.getWordsForAssociations().get(0);
//
//		label.setText(word);
//
//		Button save = new Button(TextConstants.SAVE);
//		save.addClickListener(evt -> saveAndClear());
//
//		add(label, associationField1, associationField2, associationField3, save);
	}

	private void saveAndClear()
	{
//		String word = label.getText();
//		saveNewAssociationEntity(word);
//
//		userInfo.getWordsForAssociations().remove(word);
//		if (userInfo.getWordsForAssociations().isEmpty())
//		{
//			removeAll();
//			label.setText("Fertig! Du kannst dir deine Assoziationen unter \"Statistik\" anschauen! :)");
//			add(label);
//		} else
//		{
//			String nextWord = userInfo.getWordsForAssociations().get(0);
//			label.setText(nextWord);
//			clearAssociationFields();
//		}
	}

	private void saveNewAssociationEntity(String word)
	{
//		Association associationEntity = new Association();
//		associationEntity.setUserId(userInfo.getLoggedInUser().getId());
//		associationEntity.setWord(word);
//		associationEntity.setAssociationDate(new Date(Calendar.getInstance().getTime().getTime()));
//		associationEntity.setAssociations(buildAssociations());
//		associationService.save(associationEntity);
	}

	private void clearAssociationFields()
	{
		associationField1.clear();
		associationField2.clear();
		associationField3.clear();
	}

	private String buildAssociations()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(associationField1.getValue()).append(",");
		builder.append(associationField2.getValue()).append(",");
		builder.append(associationField3.getValue());
		return builder.toString();
	}

}
