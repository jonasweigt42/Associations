package com.associations.app.exception;

public class ResetPasswordException extends Exception
{

	private static final long serialVersionUID = -52381831733012454L;

	public ResetPasswordException(String text)
	{
		super(text);
	}
	
	public ResetPasswordException(String text, Throwable e)
	{
		super(text, e);
	}
}
