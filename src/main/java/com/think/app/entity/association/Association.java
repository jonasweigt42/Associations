package com.think.app.entity.association;

import java.sql.Date;

public class Association
{

	private int userId;
	
	private String word;
	
	private String associations;
	
	private Date associationDate;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

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

}
