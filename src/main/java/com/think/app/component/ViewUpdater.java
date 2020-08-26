package com.think.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.view.ProfileView;
import com.think.app.view.StartView;
import com.think.app.view.association.AssociationGameView;
import com.think.app.view.association.StartAssociationGameView;
import com.think.app.view.association.StatisticView;
import com.vaadin.flow.spring.annotation.UIScope;

@Component
@UIScope
public class ViewUpdater
{

	@Autowired
	private AssociationGameView searchTripView;

	@Autowired
	private StartView startView;

	@Autowired
	private StartAssociationGameView provideTripView;

	@Autowired
	private ProfileView profileView;
	
	@Autowired
	private StatisticView statisticsView;
	
	public void updateViews()
	{
		searchTripView.loadContent();
		startView.loadContent();
		provideTripView.loadContent();
		profileView.loadContent();
		statisticsView.loadContent();
	}
}
