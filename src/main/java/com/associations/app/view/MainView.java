package com.associations.app.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import com.associations.app.component.ChangeLanguageComponent;
import com.associations.app.component.Logo;
import com.associations.app.component.login.Login;
import com.associations.app.constants.CSSConstants;
import com.associations.app.constants.TextConstants;
import com.associations.app.event.UpdateMainViewEvent;
import com.associations.app.view.association.game.StartAssociationGameView;
import com.associations.app.view.association.stats.YourAssociationsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import ch.qos.logback.classic.Logger;

@PWA(name = TextConstants.TITLE, shortName = TextConstants.TITLE, iconPath = "/icons/icon-512x512.png")
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/shared-styles.css")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@UIScope
@org.springframework.stereotype.Component
public class MainView extends AppLayout implements LocaleChangeObserver
{

	private static final long serialVersionUID = 8986999742090476969L;

	private Tabs menu = new Tabs();
	private FlexLayout navbarLayout = new FlexLayout();

	@Autowired
	private Logo logo;

	@Autowired
	private Login login;

	@Autowired
	private ChangeLanguageComponent clComponent;

	@Autowired
	private Logger logger;

	@PostConstruct
	public void init()
	{
		setPrimarySection(Section.DRAWER);
		addToNavbar(false, new DrawerToggle());
		addToNavbar(false, logo);
		prepareNavbarLayout();
		addToNavbar(navbarLayout);
		prepareMenuTabs();
		addToDrawer(menu);
	}

	private void prepareNavbarLayout()
	{
		navbarLayout.add(clComponent, login.getMainViewLoginButton());
		navbarLayout.addClassName(CSSConstants.MARGIN_LEFT);
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
		tabs.add(createTab(getTranslation("welcomeMainView"), StartView.class));
		tabs.add(createTab(getTranslation("gameMainView"), StartAssociationGameView.class));
		tabs.add(createTab(getTranslation("profileMainView"), ProfileView.class));
		tabs.add(createTab(getTranslation("yourAssociationsView"), YourAssociationsView.class));
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
		if (HasUrlParameter.class.isAssignableFrom(getContent().getClass()))
		{
			return;
		}
		String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
		Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
			Component child = tab.getChildren().findFirst().get();
			return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
		}).findFirst();
		tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
	}

	private void updateMainViewUI()
	{
		int selectedIndex = menu.getSelectedIndex();
		prepareMenuTabs();
		menu.setSelectedIndex(selectedIndex);
		login.updateAllLoginUis();
	}

	@EventListener
	public void onApplicationEvent(UpdateMainViewEvent event)
	{
		logger.info("catched UpdateMainViewEvent");
		updateMainViewUI();
		remove(clComponent);
	}

	@Override
	public void localeChange(LocaleChangeEvent event)
	{
		updateMainViewUI();
		clComponent.setValue(VaadinSession.getCurrent().getLocale());
	}

}
