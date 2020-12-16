package com.associations.app.view.association.game;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.association.Association;
import com.associations.app.entity.association.AssociationService;
import com.associations.app.entity.user.User;
import com.associations.app.entity.word.Word;
import com.associations.app.entity.word.WordService;
import com.associations.app.userinfo.UserInfo;
import com.associations.app.view.MainView;
import com.associations.app.view.association.stats.YourAssociationsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "game", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class AssociationGameView extends VerticalLayout implements LocaleChangeObserver, BeforeEnterObserver
{

	private static final long serialVersionUID = 4153761837545371752L;

	private TextField associationField1 = new TextField();
	private TextField associationField2 = new TextField();
	private TextField associationField3 = new TextField();
	private H4 loggedInLabel = new H4();
	private H4 notLoggedInLabel = new H4();
	private Button saveButton = new Button();
	private List<Word> words = new ArrayList<>();
	private Registration clickListenerRegistration;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private WordService wordService;

	@Autowired
	private AssociationService associationService;

	@PostConstruct
	public void init()
	{
		addClassName(CSSConstants.CENTERED_CONTENT);
		updateUi();
	}

	public void updateUi()
	{
		removeAll();

		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			addFieldsForUser(user);
		} else
		{
			notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
			add(notLoggedInLabel);
		}
	}

	private void addFieldsForUser(User user)
	{
		if (words.isEmpty())
		{
			return;
		}
		String word = words.get(0).getName();

		loggedInLabel.setText(word);
		prepareSaveButton();
		prepareTextFields();

		add(loggedInLabel, associationField1, associationField2, associationField3, saveButton);
	}

	private void prepareTextFields()
	{
		associationField1.setValueChangeMode(ValueChangeMode.EAGER);
		associationField1.addValueChangeListener(event -> checkValueForSaveButton());
		associationField2.setValueChangeMode(ValueChangeMode.EAGER);
		associationField2.addValueChangeListener(event -> checkValueForSaveButton());
		associationField3.setValueChangeMode(ValueChangeMode.EAGER);
		associationField3.addValueChangeListener(event -> checkValueForSaveButton());
	}

	private void checkValueForSaveButton()
	{
		saveButton.setEnabled(
				!associationField1.isEmpty() && !associationField2.isEmpty() && !associationField3.isEmpty());
	}

	private void prepareSaveButton()
	{
		saveButton.setText(getTranslation("save"));
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.setEnabled(false);
	}

	private void saveAndClear(String language)
	{
		String wordString = loggedInLabel.getText();
		Word word = wordService.findByNameAndLanguage(wordString, language);
		saveAssociations(word);

		words.remove(word);
		if (words.isEmpty())
		{
			removeAll();
			loggedInLabel.setText(getTranslation("finishedExercise"));
			Button button = prepareRedirectButton();
			add(loggedInLabel, button);
		} else
		{
			String nextWord = words.get(0).getName();
			loggedInLabel.setText(nextWord);
		}
		clearAssociationFields();
	}

	private Button prepareRedirectButton()
	{
		Button button = new Button();
		button.setText(getTranslation("yourAssociationsView"));
		button.getElement().addEventListener("click", event -> UI.getCurrent().navigate(YourAssociationsView.class));
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return button;
	}

	private void saveAssociations(Word word)
	{
		saveAssocitaion(word, associationField1.getValue());
		saveAssocitaion(word, associationField2.getValue());
		saveAssocitaion(word, associationField3.getValue());

		addAssociationAsWord(associationField1.getValue(), word.getLanguage());
		addAssociationAsWord(associationField2.getValue(), word.getLanguage());
		addAssociationAsWord(associationField3.getValue(), word.getLanguage());
	}

	private void saveAssocitaion(Word word, String association)
	{
		Association alreadyThere = associationService.findByKey(userInfo.getLoggedInUser().getId(), word.getId(),
				association);
		if (alreadyThere == null)
		{
			saveNewAssociation(word, association);
		} else
		{
			alreadyThere.setAssociationDate(new Date(Calendar.getInstance().getTime().getTime()));
			associationService.update(alreadyThere);
		}
	}

	private void saveNewAssociation(Word word, String association)
	{
		Association associationEntity = new Association();
		associationEntity.setUserId(userInfo.getLoggedInUser().getId());
		associationEntity.setWordId(word.getId());
		associationEntity.setAssociationDate(new Date(Calendar.getInstance().getTime().getTime()));
		associationEntity.setAssociation(association);
		associationService.save(associationEntity);
	}

	private void addAssociationAsWord(String association, String language)
	{
		if (wordService.findByNameAndLanguage(association, language) == null)
		{
			Word newWord = new Word();
			newWord.setName(association);
			newWord.setLanguage(language);
			wordService.save(newWord);
		}
	}

	private void clearAssociationFields()
	{
		associationField1.clear();
		associationField2.clear();
		associationField3.clear();
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
		saveButton.setText(getTranslation("save"));
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			if (clickListenerRegistration == null)
			{
				clickListenerRegistration = saveButton.addClickListener(evt -> saveAndClear(user.getLanguage()));
			}
			words = wordService.getRandomWords(10, user.getLanguage());
		}
		updateUi();
	}

}
