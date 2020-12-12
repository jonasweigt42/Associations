package com.associations.app.component;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class AssociationsNotification extends Notification
{

	private static final long serialVersionUID = -7205969470033550044L;
	
	public static Notification show(String text)
	{
		Notification notification = new Notification(text);
		notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.open();
        return notification;
	}
	
	public static Notification showError(String text)
	{
		Notification notification = new Notification(text);
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
        return notification;
	}
}
