package com.think.app.view.association;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.vaadin.pekkam.Canvas;
import org.vaadin.pekkam.CanvasRenderingContext2D;

import com.think.app.constants.HTMLConstants;
import com.think.app.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "canvas", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class AssociationCanvasView extends VerticalLayout
{
	private static final long serialVersionUID = -4004779308081980602L;

	private Button button = new Button("Test");
	
	@PostConstruct
	public void init()
	{
		addClassName(HTMLConstants.CENTERED_CONTENT);
		add(createCanvas(), button);
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
}
