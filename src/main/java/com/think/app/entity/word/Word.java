package com.think.app.entity.word;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "WORD")
@NamedQuery(query = "SELECT w FROM Word w WHERE w.name = ?1 AND w.language = ?2", name = "Word.findbyNameAndLanguage")
@NamedQuery(query = "SELECT w FROM Word w WHERE w.id = ?1", name = "Word.findbyId")
public class Word implements Serializable
{

	private static final long serialVersionUID = -4214785938291186327L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private String language;

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Word other = (Word) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}