package com.himo.app.component;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@Component
public class Logo extends HorizontalLayout
{

	private static final long serialVersionUID = 8539954671378937794L;

	@PostConstruct
	public void init()
	{
		Icon icon = new Icon(VaadinIcon.HOME);
		Label label = new Label("HiMo");
		add(icon, label);
	}
	
}
