package com.think.app.entity.association;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATION")
public class Association implements Serializable
{

	private static final long serialVersionUID = 521930906786420024L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int userId;
	
	@Column
	private String word;
	
	@Column
	private String associations;
	
	@Column
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

	public int getId()
	{
		return id;
	}
	
	
}
