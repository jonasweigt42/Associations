package com.think.app.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.think.app.entity.user.User;
import com.think.app.entity.user.UserDao;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserDao dao;

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
	}

	@Override
	public void save(User newUser)
	{
		dao.save(newUser);
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
	}

}
