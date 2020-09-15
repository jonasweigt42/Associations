package com.think.app.entity.user;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserDao dao;
	
	@Autowired
	private Logger logger;

	@Override
	public List<User> findAll()
	{
		return dao.findAll();
	}

	@Override
	public User getUserByMailAddress(String mailAddress)
	{
		List<User> users = dao.findAll();

		for (User user : users)
		{
			if (user.getMailAddress().equals(mailAddress))
			{
				return user;
			}
		}
		return null;
	}

	@Override
	public void update(User user)
	{
		dao.update(user);
		logger.info("updated user {}", user.getMailAddress());
	}

	@Override
	public void save(User newUser)
	{
		dao.save(newUser);
		logger.info("saved user {}", newUser.getMailAddress());
	}

	@Override
	public int count()
	{
		return dao.count();
	}

	@Override
	public void delete(User user)
	{
		dao.delete(user);
		logger.info("deleted user {}", user.getMailAddress());
	}

}