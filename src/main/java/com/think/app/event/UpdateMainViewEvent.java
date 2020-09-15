package com.think.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateMainViewEvent extends ApplicationEvent
{
	private static final long serialVersionUID = -1526554425073579653L;

	public UpdateMainViewEvent(Object source)
	{
		super(source);
	}

}
