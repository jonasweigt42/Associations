package com.associations.app.entity.verification;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "VERIFICATION_TOKEN")
@NamedQuery(query = "SELECT v FROM VerificationToken v WHERE v.token = ?1", name = "VerificationToken.findbyToken")
public class VerificationToken implements Serializable
{

	private static final long serialVersionUID = -5538243964952715037L;

	private static final int EXPIRATION = 60 * 24;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String token;

	@Column
	private int userId;

	@Column
	private Date createDate;

	@Column
	private Date expiryDate;

	public VerificationToken()
	{
		super();
	}

	public VerificationToken(final String token, final int userId)
	{
		super();
		Calendar calendar = Calendar.getInstance();
		this.token = token;
		this.userId = userId;
		this.createDate = new Date(calendar.getTime().getTime());
		this.expiryDate = calculateExpiryDate();
	}

	private Date calculateExpiryDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, EXPIRATION);
		return new Date(calendar.getTime().getTime());
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getExpiryDate()
	{
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate)
	{
		this.expiryDate = expiryDate;
	}
}
