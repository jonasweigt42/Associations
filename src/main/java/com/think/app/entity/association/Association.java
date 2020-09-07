package com.think.app.entity.association;

import java.io.Serializable;
import java.sql.Date;

public class Association implements Serializable
{

	private static final long serialVersionUID = 8214246218864102101L;

	private String userMailAddress;
	
	private String word;
	
	private String association1;
	
	private String association2;
	
	private String association3;
	
	private Date associationDate;
	
	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
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
	
	public String getAssociation1()
	{
		return association1;
	}

	public void setAssociation1(String association1)
	{
		this.association1 = association1;
	}

	public String getAssociation2()
	{
		return association2;
	}

	public void setAssociation2(String association2)
	{
		this.association2 = association2;
	}

	public String getAssociation3()
	{
		return association3;
	}

	public void setAssociation3(String association3)
	{
		this.association3 = association3;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((association1 == null) ? 0 : association1.hashCode());
		result = prime * result + ((association2 == null) ? 0 : association2.hashCode());
		result = prime * result + ((association3 == null) ? 0 : association3.hashCode());
		result = prime * result + ((userMailAddress == null) ? 0 : userMailAddress.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Association other = (Association) obj;
		if (association1 == null)
		{
			if (other.association1 != null)
				return false;
		} else if (!association1.equals(other.association1))
			return false;
		if (association2 == null)
		{
			if (other.association2 != null)
				return false;
		} else if (!association2.equals(other.association2))
			return false;
		if (association3 == null)
		{
			if (other.association3 != null)
				return false;
		} else if (!association3.equals(other.association3))
			return false;
		if (userMailAddress == null)
		{
			if (other.userMailAddress != null)
				return false;
		} else if (!userMailAddress.equals(other.userMailAddress))
			return false;
		if (word == null)
		{
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
}
