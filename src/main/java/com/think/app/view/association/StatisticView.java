package com.think.app.view.association;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.constants.LanguageConstants;
import com.think.app.entity.association.Association;
import com.think.app.entity.association.AssociationService;
import com.think.app.entity.user.User;
import com.think.app.entity.word.WordService;
import com.think.app.textresources.TCResourceBundle;
import com.think.app.userinfo.UserInfo;
import com.think.app.view.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
	private WordService wordService;
	
	@Autowired
	private TCResourceBundle tcResourceBundle;

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
			removeClassName(HTMLConstants.CENTERED_CONTENT);
			addFieldsForUser(user);
		} else
		{
			addClassName(HTMLConstants.CENTERED_CONTENT);
			H4 label = new H4(tcResourceBundle.get(LanguageConstants.NOT_LOGGED_IN_MESSAGE));
			add(label);
		}
	}

	public void addFieldsForUser(User user)
	{
		List<Association> datalist = associationService.findByUserId(user.getId());
		ListDataProvider<Association> dataProvider = new ListDataProvider<>(datalist);
		logger.info("for user {} exists {} associations", user.getMailAddress(), datalist.size());

		Grid<Association> grid = prepareGrid(dataProvider);

		add(grid);
	}

	private Grid<Association> prepareGrid(ListDataProvider<Association> dataProvider)
	{
		Grid<Association> grid = new Grid<>();
		grid.setDataProvider(dataProvider);
		grid.setSizeUndefined();

		addColumns(dataProvider, grid);
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
		        GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

		return grid;
	}

	private void addColumns(ListDataProvider<Association> dataProvider, Grid<Association> grid)
	{
		Grid.Column<Association> wordColumn = grid.addColumn(association -> wordService.findById(association.getWordId()))
				.setHeader(tcResourceBundle.get(LanguageConstants.GRID_HEADER_WORD));

		Grid.Column<Association> association1Column = grid.addColumn(Association::getAssociation1)
				.setHeader(tcResourceBundle.get(LanguageConstants.GRID_HEADER_ASSOCIATION_1));
		Grid.Column<Association> association2Column = grid.addColumn(Association::getAssociation2)
				.setHeader(tcResourceBundle.get(LanguageConstants.GRID_HEADER_ASSOCIATION_2));
		Grid.Column<Association> association3Column = grid.addColumn(Association::getAssociation3)
				.setHeader(tcResourceBundle.get(LanguageConstants.GRID_HEADER_ASSOCIATION_3));

		Grid.Column<Association> dateColumn = grid.addColumn(Association::getAssociationDate)
				.setHeader(tcResourceBundle.get(LanguageConstants.GRID_HEADER_DATE));

		HeaderRow filterRow = grid.appendHeaderRow();

		addWordColumn(dataProvider, filterRow, wordColumn);

		addAssociation1Column(dataProvider, filterRow, association1Column);
		addAssociation2Column(dataProvider, filterRow, association2Column);
		addAssociation3Column(dataProvider, filterRow, association3Column);

		addDateColumn(dataProvider, filterRow, dateColumn);
	}

	private void addDateColumn(ListDataProvider<Association> dataProvider, HeaderRow filterRow,
			Grid.Column<Association> dateColumn)
	{
		TextField dateField = new TextField();
		dateField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociationDate().toString(), dateField.getValue().toString())));

		dateField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(dateColumn).setComponent(dateField);
		dateField.setSizeFull();
		dateField.setPlaceholder(tcResourceBundle.get(LanguageConstants.FILTER));
	}
	
	private void addAssociation1Column(ListDataProvider<Association> dataProvider, HeaderRow filterRow,
			Grid.Column<Association> associationsColumn)
	{
		TextField associationsField = new TextField();
		associationsField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociation1(), associationsField.getValue())));

		associationsField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(associationsColumn).setComponent(associationsField);
		associationsField.setSizeFull();
		associationsField.setPlaceholder(tcResourceBundle.get(LanguageConstants.FILTER));
	}

	private void addAssociation2Column(ListDataProvider<Association> dataProvider, HeaderRow filterRow,
			Grid.Column<Association> associationsColumn)
	{
		TextField associationsField = new TextField();
		associationsField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociation2(), associationsField.getValue())));

		associationsField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(associationsColumn).setComponent(associationsField);
		associationsField.setSizeFull();
		associationsField.setPlaceholder(tcResourceBundle.get(LanguageConstants.FILTER));
	}

	private void addAssociation3Column(ListDataProvider<Association> dataProvider, HeaderRow filterRow,
			Grid.Column<Association> associationsColumn)
	{
		TextField associationsField = new TextField();
		associationsField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociation3(), associationsField.getValue())));

		associationsField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(associationsColumn).setComponent(associationsField);
		associationsField.setSizeFull();
		associationsField.setPlaceholder(tcResourceBundle.get(LanguageConstants.FILTER));
	}

	private void addWordColumn(ListDataProvider<Association> dataProvider, HeaderRow filterRow,
			Grid.Column<Association> wordColumn)
	{

		TextField wordField = new TextField();
		wordField.addValueChangeListener(event -> dataProvider
				.addFilter(association -> StringUtils.containsIgnoreCase(wordService.findById(association.getWordId()).getName(), wordField.getValue())));

		wordField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(wordColumn).setComponent(wordField);
		wordField.setSizeFull();
		wordField.setPlaceholder(tcResourceBundle.get(LanguageConstants.FILTER));
	}
}
