package com.think.app.entity.association;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
	private int wordId;
	
	@NotEmpty
	@Column
	private String association;
	
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

	public int getWordId()
	{
		return wordId;
	}

	public void setWordId(int wordId)
	{
		this.wordId = wordId;
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
	
	public String getAssociation()
	{
		return association;
	}

	public void setAssociation(String association)
	{
		this.association = association;
	}

}