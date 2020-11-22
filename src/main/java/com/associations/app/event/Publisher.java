package com.associations.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher
{

	@Autowired
	private final ApplicationEventPublisher applicationPublisher;

	public Publisher(ApplicationEventPublisher applicationPublisher)
	{
		this.applicationPublisher = applicationPublisher;
	}

	public void publishEvent(ApplicationEvent event)
	{
		applicationPublisher.publishEvent(event);
	}

}
