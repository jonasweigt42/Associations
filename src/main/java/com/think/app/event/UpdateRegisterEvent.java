package com.think.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateRegisterEvent extends ApplicationEvent
{

	private static final long serialVersionUID = -908812069857988107L;

	public UpdateRegisterEvent(Object source)
	{
		super(source);
	}

}
