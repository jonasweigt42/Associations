package com.think.app.view.association;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
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
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
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
		List<Association> datalist = associationService.findByUserId(user.getId());
		ListDataProvider<Association> dataProvider = new ListDataProvider<>(datalist);
		logger.info("for user " + user.getMailAddress() + " exists " + datalist.size() + " associations");

		Grid<Association> grid = prepareGrid(dataProvider);

		add(grid);
	}

	private Grid<Association> prepareGrid(ListDataProvider<Association> dataProvider)
	{
		Grid<Association> grid = new Grid<>();
		grid.setDataProvider(dataProvider);
		grid.setSizeUndefined();

		addColumns(dataProvider, grid);
		
		return grid;
	}

	private void addColumns(ListDataProvider<Association> dataProvider, Grid<Association> grid)
	{
		Grid.Column<Association> wordColumn = grid.addColumn(Association::getWord).setHeader("Wort");

		Grid.Column<Association> associationsColumn = grid.addColumn(Association::getAssociations)
				.setHeader("Assoziationen");

		Grid.Column<Association> dateColumn = grid.addColumn(Association::getAssociationDate).setHeader("Datum");

		HeaderRow filterRow = grid.appendHeaderRow();

		addWordColumn(dataProvider, grid, filterRow, wordColumn);

		addAssociationsColumn(dataProvider, grid, filterRow, associationsColumn);

		addDateColumn(dataProvider, grid, filterRow, dateColumn);
	}

	private void addDateColumn(ListDataProvider<Association> dataProvider, Grid<Association> grid, HeaderRow filterRow,
			Grid.Column<Association> dateColumn)
	{
		TextField dateField = new TextField();
		dateField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociationDate().toString(), dateField.getValue().toString())));

		dateField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(dateColumn).setComponent(dateField);
		dateField.setSizeFull();
		dateField.setPlaceholder(TextConstants.FILTER);
	}

	private void addAssociationsColumn(ListDataProvider<Association> dataProvider, Grid<Association> grid,
			HeaderRow filterRow, Grid.Column<Association> associationsColumn)
	{
		TextField associationsField = new TextField();
		associationsField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociations(), associationsField.getValue())));

		associationsField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(associationsColumn).setComponent(associationsField);
		associationsField.setSizeFull();
		associationsField.setPlaceholder(TextConstants.FILTER);
	}

	private void addWordColumn(ListDataProvider<Association> dataProvider, Grid<Association> grid, HeaderRow filterRow,
			Grid.Column<Association> wordColumn)
	{

		TextField wordField = new TextField();
		wordField.addValueChangeListener(event -> dataProvider
				.addFilter(association -> StringUtils.containsIgnoreCase(association.getWord(), wordField.getValue())));

		wordField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(wordColumn).setComponent(wordField);
		wordField.setSizeFull();
		wordField.setPlaceholder(TextConstants.FILTER);
	}
}
