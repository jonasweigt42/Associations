package com.think.app.view.association;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.pekkam.Canvas;
import org.vaadin.pekkam.CanvasRenderingContext2D;

import com.think.app.constants.HTMLConstants;
import com.think.app.entity.association.AssociationService;
import com.think.app.entity.user.User;
import com.think.app.entity.word.Word;
import com.think.app.entity.word.WordService;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "canvas", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class AssociationCanvasView extends VerticalLayout implements LocaleChangeObserver
{
	private static final long serialVersionUID = -4004779308081980602L;

	private Button button = new Button("Test");

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

		updateUi();

	}

	public void updateUi()
	{
		removeAll();
		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			HorizontalLayout layout = new HorizontalLayout();
			Set<Integer> wordIds = associationService.findByUserId(userInfo.getLoggedInUser().getId()).stream()
					.map(a -> a.getWordId()).collect(Collectors.toSet());

			for (int wordId : wordIds)
			{
				Word word = wordService.findById(wordId);
				if (word.getLanguage().equals(VaadinSession.getCurrent().getLocale().getLanguage()))
				{
					layout.add(new Button(word.getName()));
				}
			}
			add(layout);
//			add(createCanvas(), button);
		} else
		{
			H4 label = new H4(getTranslation("notLoggedInMessage"));
			add(label);
		}

	}

	private Canvas createCanvas()
	{
		Canvas canvas = new Canvas(400, 400);
		CanvasRenderingContext2D ctx = canvas.getContext();
		ctx.setStrokeStyle("red");
		ctx.beginPath();
		ctx.moveTo(0, 0);
		ctx.lineTo(400, 400);
		ctx.fillText("Test", 40, 40);
		ctx.rect(20, 20, 100, 100);
		ctx.arc(100, 100, 100, 90, 0, true);
		ctx.closePath();
		ctx.stroke();

		return canvas;
	}

	public Button getButton()
	{
		return button;
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		updateUi();
	}
}
