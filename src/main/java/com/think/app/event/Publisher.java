package com.think.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher
{

	@Autowired
	private final ApplicationEventPublisher publisher;

	Publisher(ApplicationEventPublisher publisher)
	{
		this.publisher = publisher;
	}

	public void publishEvent(ApplicationEvent event)
	{
		publisher.publishEvent(event);
	}

}
