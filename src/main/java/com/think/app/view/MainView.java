package com.think.app.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.think.app.component.Login;
import com.think.app.component.Logo;
import com.think.app.view.association.StartAssociationGameView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
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

@PWA(name = "Think Connected Game", shortName = "TCGame")
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/shared-styles.css")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@UIScope
public class MainView extends AppLayout
{

	private static final long serialVersionUID = 8986999742090476969L;

	private Tabs menu;

	@Autowired
	private Logo logo;

	@Autowired
	private Login login;

	@PostConstruct
	public void init() throws IOException, URISyntaxException
	{
		setPrimarySection(Section.DRAWER);
		addToNavbar(false, new DrawerToggle());
		addToNavbar(false, logo);
		addToNavbar(login.getLoginButton());
		menu = createMenuTabs();
		addToDrawer(menu);
		login.open();
	}
	
	private Tabs createMenuTabs()
	{
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
		tabs.setId("tabs");
		tabs.add(getAvailableTabs());
		return tabs;
	}

	private Tab[] getAvailableTabs()
	{
		final List<Tab> tabs = new ArrayList<>();
		tabs.add(createTab("Willkommen", StartView.class));
		tabs.add(createTab("Spiel",StartAssociationGameView.class));
		tabs.add(createTab("Profil", ProfileView.class));
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

}
