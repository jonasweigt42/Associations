package com.associations.app.view.association.stats;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.component.buttons.AssociationCycleButton;
import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.association.Association;
import com.associations.app.entity.association.AssociationService;
import com.associations.app.entity.user.User;
import com.associations.app.entity.word.Word;
import com.associations.app.entity.word.WordService;
import com.associations.app.userinfo.UserInfo;
import com.associations.app.view.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "words", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class YourAssociationsView extends VerticalLayout implements LocaleChangeObserver
{
	private static final long serialVersionUID = -4004779308081980602L;

	@Autowired
	private AssociationService associationService;

	@Autowired
	private WordService wordService;

	@Autowired
	private UserInfo userInfo;

	@PostConstruct
	public void init()
	{
		addClassName(CSSConstants.CENTERED_CONTENT);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setAlignItems(Alignment.CENTER);

		updateUi();

	}

	public void updateUi()
	{
		removeAll();
		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			
			H4 label = new H4(getTranslation("yourAssociations"));
			add(label);
			
			Set<Integer> wordIds = associationService.findByUserId(userInfo.getLoggedInUser().getId()).stream()
					.map(Association::getWordId).collect(Collectors.toSet());

			addAssociationsToView(wordIds);
		} else
		{
			H4 label = new H4(getTranslation("notLoggedInMessage"));
			add(label);
		}

	}

	private void addAssociationsToView(Set<Integer> wordIds)
	{
		for (int wordId : wordIds)
		{
			Word word = wordService.findById(wordId);
			if (word.getLanguage().equals(VaadinSession.getCurrent().getLocale().getLanguage()))
			{
				add(new AssociationCycleButton(word.getName()));
			}
		}
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		updateUi();
	}
}
