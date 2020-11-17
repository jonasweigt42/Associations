package com.think.app.view.association.stats;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.think.app.constants.HTMLConstants;
import com.think.app.entity.association.Association;
import com.think.app.entity.association.AssociationService;
import com.think.app.entity.user.User;
import com.think.app.entity.word.WordService;
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
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "stats", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
@Component
public class StatisticView extends VerticalLayout implements LocaleChangeObserver
{

	private static final long serialVersionUID = -1660640162656141470L;
	
	private H4 notLoggedInLabel = new H4();
	
	@Autowired
	private UserInfo userInfo;

	@Autowired
	private AssociationService associationService;
	
	@Autowired
	private WordService wordService;

	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		updateUi();
	}

	public void updateUi()
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
			notLoggedInLabel.setText(getTranslation("notLoggedInMessage"));
			add(notLoggedInLabel);
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
				.setHeader(getTranslation("gridHeaderWord"));

		Grid.Column<Association> association1Column = grid.addColumn(Association::getAssociation)
				.setHeader(getTranslation("gridHeaderAssociation"));

		Grid.Column<Association> dateColumn = grid.addColumn(Association::getAssociationDate)
				.setHeader(getTranslation("gridHeaderDate"));

		HeaderRow filterRow = grid.appendHeaderRow();

		addWordColumn(dataProvider, filterRow, wordColumn);

		addAssociation1Column(dataProvider, filterRow, association1Column);

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
		dateField.setPlaceholder(getTranslation("filter"));
	}
	
	private void addAssociation1Column(ListDataProvider<Association> dataProvider, HeaderRow filterRow,
			Grid.Column<Association> associationsColumn)
	{
		TextField associationsField = new TextField();
		associationsField.addValueChangeListener(event -> dataProvider.addFilter(association -> StringUtils
				.containsIgnoreCase(association.getAssociation(), associationsField.getValue())));

		associationsField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(associationsColumn).setComponent(associationsField);
		associationsField.setSizeFull();
		associationsField.setPlaceholder(getTranslation("filter"));
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
		wordField.setPlaceholder(getTranslation("filter"));
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		updateUi();
	}
}