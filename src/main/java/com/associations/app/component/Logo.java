package com.associations.app.component;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.view.StartView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;

@Component
@UIScope
public class Logo extends HorizontalLayout
{

	private static final long serialVersionUID = 8539954671378937794L;

	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		File file = new File("icon.png");

		Image image = new Image(new StreamResource("icon.png", () -> {
			try
			{
				return new FileInputStream(file);
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			return null;
		}), "");
		image.setHeight("40px");
		image.setWidth("40px");
		image.addClickListener(event -> UI.getCurrent().navigate(StartView.class));
		add(image);
	}

}
