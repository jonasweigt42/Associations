package com.associations.app.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@NamedQuery(query = "SELECT u FROM User u WHERE u.id = ?1", name = "User.findbyId")
@NamedQuery(query = "SELECT u FROM User u WHERE u.mailAddress = ?1", name = "User.findbymailAddress")
public class User implements Serializable
{

	private static final long serialVersionUID = 2517077877917932578L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String mailAddress;
	@Column
	private String password;
	@Column
	private String language;
	@Column
	private boolean enabled;
	
	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public String getLanguage()
	{
		return language;
	}
	
	public void setLanguage(String language)
	{
		this.language = language;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getMailAddress()
	{
		return mailAddress;
	}
	
	public void setMailAddress(String mailAddress)
	{
		this.mailAddress = mailAddress;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
}