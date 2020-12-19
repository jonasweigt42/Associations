package com.associations.app.exception;

public class MailException extends Exception
{

	private static final long serialVersionUID = -52381831733012454L;

	public MailException(String text)
	{
		super(text);
	}
	
	public MailException(String text, Throwable e)
	{
		super(text, e);
	}
}
