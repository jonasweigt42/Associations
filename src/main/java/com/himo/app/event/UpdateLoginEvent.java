package com.himo.app.event;

import org.springframework.context.ApplicationEvent;

public class UpdateLoginEvent extends ApplicationEvent
{

	private static final long serialVersionUID = 7072320514957659771L;
	
	public UpdateLoginEvent(Object source)
	{
		super(source);
		// TODO Auto-generated constructor stub
	}


}
