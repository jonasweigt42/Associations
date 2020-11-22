package com.associations.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.view.ProfileView;
import com.associations.app.view.StartView;
import com.associations.app.view.association.game.AssociationGameView;
import com.associations.app.view.association.game.StartAssociationGameView;
import com.associations.app.view.association.stats.YourAssociationsView;
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
	private YourAssociationsView yourAssociationsView;
	
	public void updateViews()
	{
		searchTripView.updateUi();
		startView.updateUi();
		provideTripView.updateUi();
		profileView.updateUi();
		yourAssociationsView.updateUi();
	}
	
}
