package com.associations.app.component;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.associations.app.view.StartView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

@Component
@UIScope
public class Logo extends HorizontalLayout
{

	private static final long serialVersionUID = 8539954671378937794L;

	@PostConstruct
	public void init()
	{

		Image image = new Image("frontend/icon.png", "");
		image.setHeight("40px");
		image.setWidth("40px");
		image.addClickListener(event -> UI.getCurrent().navigate(StartView.class));
		add(image);
	}

}
