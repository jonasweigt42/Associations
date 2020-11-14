package com.think.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.view.ProfileView;
import com.think.app.view.StartView;
import com.think.app.view.association.game.AssociationGameView;
import com.think.app.view.association.game.StartAssociationGameView;
import com.think.app.view.association.stats.AssociationWordsView;
import com.think.app.view.association.stats.StatisticView;
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
	
	@Autowired
	private AssociationWordsView canvasView;
	
	public void updateViews()
	{
		searchTripView.updateUi();
		startView.updateUi();
		provideTripView.updateUi();
		profileView.updateUi();
		statisticsView.updateUi();
		canvasView.updateUi();
	}
	
}
