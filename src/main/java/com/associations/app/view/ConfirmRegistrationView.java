package com.associations.app.view;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.associations.app.component.login.Login;
import com.associations.app.constants.CSSConstants;
import com.associations.app.entity.user.User;
import com.associations.app.entity.user.UserService;
import com.associations.app.entity.verification.VerificationToken;
import com.associations.app.entity.verification.VerificationTokenService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "confirmRegistration")
@CssImport("./styles/shared-styles.css")
@UIScope
@Component
public class ConfirmRegistrationView extends VerticalLayout implements HasUrlParameter<String>
{

	private static final long serialVersionUID = -931758802756596220L;

	@Autowired
	private VerificationTokenService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Login login;
	
	@Autowired
	private Logger logger;
	
	private Label label = new Label();
	
	@PostConstruct
	public void init()
	{
		addClassName(CSSConstants.CENTERED_CONTENT);
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter)
	{
		removeAll();
		VerificationToken verificationToken = service.findByToken(parameter);
		if (verificationToken == null)
		{
			logger.error("invalid token!");
			label.setText(getTranslation("genericError"));
			add(label);
			return;
		}
		Calendar calendar = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0)
		{
			logger.info("token expired");
			label.setText(getTranslation("verificationExpired"));
			add(label);
			return;
		}
		enableUser(verificationToken);
		addBackToLoginButton();
		
	}

	private void addBackToLoginButton()
	{
		Button button = new Button(getTranslation("toLogin"));
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.addClickListener(clickEvent -> navigateToLogin());
		add(button);
	}

	private void enableUser(VerificationToken verificationToken)
	{
		User user = userService.findById(verificationToken.getUserId());
		user.setEnabled(true);
		userService.update(user);
		label.setText(getTranslation("confirmationSucceed"));
		add(label);
	}
	
	private void navigateToLogin()
	{
		UI.getCurrent().navigate(StartView.class);
		login.open();
	}

}
