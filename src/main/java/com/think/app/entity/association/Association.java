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
	private String association1;
	
	@Column
	private String association2;
	
	@Column
	private String association3;
	
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

	
}