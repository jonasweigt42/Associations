package com.associations.app.component;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.user.User;
import com.associations.app.entity.user.UserService;
import com.associations.app.translation.TranslationProvider;
import com.associations.app.userinfo.UserInfo;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Component
public class ChangeLanguageComponent extends FlexLayout
{

	private static final long serialVersionUID = 447578537698949511L;

	private Icon icon = new Icon(VaadinIcon.FLAG_O);

	private ComboBox<Locale> clCombobox = new ComboBox<>();
	
	@Autowired
	private TranslationProvider translationProvider;
	
	@Autowired
	private UserInfo userInfo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Logger logger;
	
	@PostConstruct
	public void init()
	{
		icon.setClassName(CSSConstants.FLAG_ICON);
		addClassName(CSSConstants.MARGIN_LEFT);
		updateChangeLanguageBox();
		add(icon, clCombobox);
	}
	
	private void updateChangeLanguageBox()
	{
		clCombobox.setWidth("72px");
		clCombobox.setItems(translationProvider.getProvidedLocales());
		clCombobox.addValueChangeListener(event -> {
			VaadinSession.getCurrent().setLocale(event.getValue());
			User user = userInfo.getLoggedInUser();
			if (user != null)
			{
				user.setLanguage(event.getValue().getLanguage());
				logger.info("changed language to {}", event.getValue().getLanguage());
				userService.update(user);
			}
		});
	}
	
	public void setValue(Locale locale)
	{
		clCombobox.setValue(locale);
	}
	
	public Icon getIcon()
	{
		return icon;
	}

	public ComboBox<Locale> getClCombobox()
	{
		return clCombobox;
	}
}
