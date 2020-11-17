package com.think.app.component.buttons;

import com.think.app.view.association.stats.AssociationCycleView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class CanvasButton extends Button
{

	private static final long serialVersionUID = 1284935180147327165L;

	public CanvasButton(String wordName)
	{
		setText(wordName);
		addClickListener(event -> UI.getCurrent().navigate(AssociationCycleView.class, wordName));
		addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	}
}
