package com.think.app.view.association;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.pekkam.Canvas;

import com.think.app.constants.HTMLConstants;
import com.think.app.entity.association.Association;
import com.think.app.entity.association.AssociationService;
import com.think.app.entity.user.User;
import com.think.app.entity.word.Word;
import com.think.app.entity.word.WordService;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
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
public class AssociationCanvasView extends VerticalLayout implements HasUrlParameter<String>
{

	private static final long serialVersionUID = 1005150066090728627L;

	@Autowired
	private UserInfo userInfo;

	private Canvas canvas;

	private H4 label = new H4();
	
	@Autowired
	private WordService wordService;
	
	@Autowired
	private AssociationService associationService;

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
//			prepareCanvas();
		} else
		{
			H4 label = new H4(getTranslation("notLoggedInMessage"));
			add(label);
		}
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter)
	{
		removeAll();
//		CanvasRenderingContext2D ctx = canvas.getContext();
//		ctx.fillText(parameter, 50, 50);
		label.setText(parameter);
		add(label);
		addAssociations(parameter);
	}

	private void addAssociations(String wordText)
	{
		Word word = wordService.findByNameAndLanguage(wordText, VaadinSession.getCurrent().getLocale().getLanguage());
		List<Association> associations = associationService.findByWordId(word.getId());
		for(Association association : associations)
		{
			Button button = new Button(association.getAssociation());
			button.getElement().setProperty("title", association.getAssociationDate().toString());
			add(button);
		}
	}
	
//	private void prepareCanvas()
//	{
//		canvas = new Canvas(400, 400);
//		CanvasRenderingContext2D ctx = canvas.getContext();
//		ctx.setStrokeStyle("red");
//		ctx.beginPath();
//		ctx.moveTo(0, 0);
//		ctx.lineTo(400, 400);
//
//		ctx.rect(20, 20, 100, 100);
//		ctx.arc(100, 100, 100, 90, 0, true);
//		ctx.rotate(20);
//		ctx.closePath();
//		ctx.stroke();
//	}

}
