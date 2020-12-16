package com.associations.app.view.association.stats;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.component.buttons.AssociationCycleButton;
import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.association.Association;
import com.associations.app.entity.association.AssociationService;
import com.associations.app.entity.word.Word;
import com.associations.app.entity.word.WordService;
import com.associations.app.view.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "association", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class YourAssociationsCycleView extends VerticalLayout implements HasUrlParameter<String>
{

	private static final long serialVersionUID = 1005150066090728627L;

	private H4 label = new H4();

	@Autowired
	private WordService wordService;

	@Autowired
	private AssociationService associationService;

	@PostConstruct
	public void init()
	{
		addClassName(CSSConstants.CENTERED_CONTENT);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setAlignItems(Alignment.CENTER);

	}

	@Override
	public void setParameter(BeforeEvent event, String parameter)
	{
		removeAll();
		label.setText(parameter);
		add(label);
		addAssociations(parameter);
	}

	private void addAssociations(String wordText)
	{
		Word word = wordService.findByNameAndLanguage(wordText, VaadinSession.getCurrent().getLocale().getLanguage());
		if (word == null)
		{
			return;
		}
		List<Association> associations = associationService.findByWordId(word.getId());
		for (Association association : associations)
		{
			Word associatedWord = wordService.findByNameAndLanguage(association.getAssociation(),
					VaadinSession.getCurrent().getLocale().getLanguage());
			if (associatedWord == null || associationService.findByWordId(associatedWord.getId()).isEmpty())
			{
				Label wordLabel = new Label(association.getAssociation());
				add(wordLabel);
			} else
			{
				AssociationCycleButton button = new AssociationCycleButton(association.getAssociation());
				button.getElement().setProperty("title", association.getAssociationDate().toString());
				add(button);
			}
		}
	}

}
