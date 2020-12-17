package com.associations.app.view.association.game;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.user.User;
import com.associations.app.userinfo.UserInfo;
import com.associations.app.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "startGame", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class StartAssociationGameView extends VerticalLayout implements LocaleChangeObserver
{

	private static final long serialVersionUID = -6374720069983434221L;

	private H4 notLoggedInLabel = new H4();
	private H4 startExerciseLabel = new H4();
	private ComboBox<Integer> countOfWords = new ComboBox<>();
	private Button startButton = new Button();

	@Autowired
	private UserInfo userInfo;

	@PostConstruct
	public void init()
	{
		addClassName(CSSConstants.CENTERED_CONTENT);
		startButton.addClickListener(evt -> navigate());
		updateUi();
	}

	public void updateUi()
	{
		removeAll();

		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			addFieldsForUser();
		} else
		{
			notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
			add(notLoggedInLabel);
		}
	}

	public void addFieldsForUser()
	{
		startExerciseLabel.setText(getTranslation("exercise"));
		
		Integer[] array =
			{ 3, 5, 10 };
		
		countOfWords.setItems(Arrays.asList(array));
		countOfWords.setValue(10);
		
		startButton.setText(getTranslation("start"));
		startButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(startExerciseLabel, countOfWords, startButton);
	}

	private void navigate()
	{
		UI.getCurrent().navigate(AssociationGameView.class, countOfWords.getValue());
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
		startExerciseLabel.setText(getTranslation("exercise"));
		startButton.setText(getTranslation("start"));
	}

}
