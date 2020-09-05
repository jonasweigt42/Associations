package com.think.app.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.think.app.component.ChangeLanguageButton;
import com.think.app.component.Login;
import com.think.app.component.Logo;
import com.think.app.component.ViewUpdater;
import com.think.app.constants.LanguageConstants;
import com.think.app.constants.TextConstants;
import com.think.app.textresources.TCResourceBundle;
import com.think.app.view.association.StartAssociationGameView;
import com.think.app.view.association.StatisticView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean. Use the @PWA
 * annotation make the application installable on phones, tablets and some
 * desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 */

@PWA(name = "Think Connected Game", shortName = TextConstants.TITLE)
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/shared-styles.css")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@UIScope
public class MainView extends AppLayout
{

	private static final long serialVersionUID = 8986999742090476969L;

	private Tabs menu = new Tabs();

	@Autowired
	private Logo logo;

	@Autowired
	private Login login;
	
	@Autowired
	private ChangeLanguageButton clButton;
	
	@Autowired
	private TCResourceBundle tcResourceBundle;
	
	@Autowired
	private ViewUpdater viewUpdater;
	
	@PostConstruct
	public void init() throws IOException, URISyntaxException
	{
		setPrimarySection(Section.DRAWER);
		addToNavbar(false, new DrawerToggle());
		addToNavbar(false, logo);
		addToNavbar(prepareButtonLayout());
		prepareMenuTabs();
		addToDrawer(menu);
	}

	private HorizontalLayout prepareButtonLayout()
	{
		HorizontalLayout layout = new HorizontalLayout();
		clButton.addClickListener(evt -> toggleLanguageButton());
		layout.add(clButton, login.getLoginButton());
		layout.addClassName("margin-left");
		return layout;
	}
	
	private void prepareMenuTabs()
	{
		menu.removeAll();
		menu.setOrientation(Tabs.Orientation.VERTICAL);
		menu.addThemeVariants(TabsVariant.LUMO_CENTERED);
		menu.setId("tabs");
		menu.add(getAvailableTabs());
	}

	private Tab[] getAvailableTabs()
	{
		final List<Tab> tabs = new ArrayList<>();
		tabs.add(createTab(tcResourceBundle.get(LanguageConstants.WELCOME_MAIN_VIEW), StartView.class));
		tabs.add(createTab(tcResourceBundle.get(LanguageConstants.GAME_MAIN_VIEW),StartAssociationGameView.class));
		tabs.add(createTab(tcResourceBundle.get(LanguageConstants.PROFILE_MAIN_VIEW), ProfileView.class));
		tabs.add(createTab(tcResourceBundle.get(LanguageConstants.STATISTICS_MAIN_VIEW), StatisticView.class));
		return tabs.toArray(new Tab[tabs.size()]);
	}

	private Tab createTab(String title, Class<? extends Component> viewClass)
	{
		return createTab(populateLink(new RouterLink(null, viewClass), title));
	}

	private Tab createTab(Component content)
	{
		final Tab tab = new Tab();
		tab.add(content);
		return tab;
	}

	private <T extends HasComponents> T populateLink(T a, String title)
	{
		a.add(title);
		return a;
	}

	@Override
	protected void afterNavigation()
	{
		super.afterNavigation();
		selectTab();
	}

	private void selectTab()
	{
		String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
		Optional<Component> tabToSelect = menu.getChildren().filter(tab ->
		{
			Component child = tab.getChildren().findFirst().get();
			return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
		}).findFirst();
		tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
	}
	
	private void updateUI()
	{
		prepareMenuTabs();
		login.updateUI();
	}
	
	private void toggleLanguageButton()
	{
		clButton.toggle();
		updateUI();
		viewUpdater.updateViews();
	}

}
