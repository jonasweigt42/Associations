package com.think.app.view.association;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.TextConstants;
import com.think.app.entity.association.Association;
import com.think.app.entity.user.User;
import com.think.app.service.association.AssociationService;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "stats", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class StatisticView extends VerticalLayout
{

	private static final long serialVersionUID = -1660640162656141470L;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private AssociationService associationService;
	
	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		loadContent();
	}

	public void loadContent()
	{
		removeAll();

		User user = userInfo.getLoggedInUser();
		if (user != null)
		{
			addFieldsForUser(user);
		} else
		{
			H4 label = new H4(TextConstants.NOT_LOGGED_IN_MESSAGE);
			add(label);
		}
	}

	public void addFieldsForUser(User user)
	{
		List<Association> list = associationService.findByUserId(user.getId());
		logger.info("for user " + user.getMailAddress() + " exists " + list.size() + " associations");
		
		Grid<Association> grid = new Grid<>(Association.class);
		grid.setSizeFull();
		grid.setItems(list);
		grid.removeColumnByKey("id");
		grid.removeColumnByKey("userId");

		add(grid);
	}
}
