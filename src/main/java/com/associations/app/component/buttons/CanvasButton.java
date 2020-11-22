package com.associations.app.component.buttons;

import com.associations.app.view.association.stats.YourAssociationsCycleView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class CanvasButton extends Button
{

	private static final long serialVersionUID = 1284935180147327165L;

	public CanvasButton(String wordName)
	{
		setText(wordName);
		addClickListener(event -> UI.getCurrent().navigate(YourAssociationsCycleView.class, wordName));
		addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	}
}
