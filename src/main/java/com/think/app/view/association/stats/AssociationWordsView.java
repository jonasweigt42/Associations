package com.think.app.view.association.stats;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.component.buttons.CanvasButton;
import com.think.app.constants.HTMLConstants;
import com.think.app.entity.association.AssociationService;
import com.think.app.entity.user.User;
import com.think.app.entity.word.Word;
import com.think.app.entity.word.WordService;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
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
public class AssociationWordsView extends VerticalLayout implements LocaleChangeObserver
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
		addClassName(HTMLConstants.CENTERED_CONTENT);
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
					.map(a -> a.getWordId()).collect(Collectors.toSet());

			for (int wordId : wordIds)
			{
				Word word = wordService.findById(wordId);
				if (word.getLanguage().equals(VaadinSession.getCurrent().getLocale().getLanguage()))
				{
					add(new CanvasButton(word.getName()));
				}
			}
		} else
		{
			H4 label = new H4(getTranslation("notLoggedInMessage"));
			add(label);
		}

	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		updateUi();
	}
}
