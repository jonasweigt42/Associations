package com.think.app.component.buttons;

import com.think.app.view.association.AssociationCanvasView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

public class CanvasButton extends Button
{

	private static final long serialVersionUID = 1284935180147327165L;

	public CanvasButton(String wordName)
	{
		setText(wordName);
		addClickListener(event -> UI.getCurrent().navigate(AssociationCanvasView.class, wordName));
	}
}
