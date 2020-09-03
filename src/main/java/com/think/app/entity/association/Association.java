package com.think.app.entity.association;

import java.io.Serializable;
import java.sql.Date;

public class Association implements Serializable
{

	private static final long serialVersionUID = 8214246218864102101L;

	private String userMailAddress;
	
	private String word;
	
	private String associations;
	
	private Date associationDate;
	
	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

	public String getAssociations()
	{
		return associations;
	}

	public void setAssociations(String associations)
	{
		this.associations = associations;
	}

	public Date getAssociationDate()
	{
		return associationDate;
	}

	public void setAssociationDate(Date associationDate)
	{
		this.associationDate = associationDate;
	}

	public String getUserMailAddress()
	{
		return userMailAddress;
	}

	public void setUserMailAddress(String userMailAddress)
	{
		this.userMailAddress = userMailAddress;
	}

}
