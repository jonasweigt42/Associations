package com.think.app.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.think.app.entity.user.User;
import com.think.app.service.user.UserService;

@RestController
public class UserController
{

	@Autowired
	private UserService userService;

	@GetMapping("/getUser")
	public User getPatient(@RequestParam String mailAddress) throws InterruptedException, ExecutionException
	{
		return userService.getUser(mailAddress);
	}

	@PostMapping("/createUser")
	public String createPatient(@RequestBody User user) throws InterruptedException, ExecutionException
	{
		return userService.saveUser(user);
	}

	@PutMapping("/updateUser")
	public String updatePatient(@RequestBody User user) throws InterruptedException, ExecutionException
	{
		return userService.updateUser(user);
	}

	@DeleteMapping("/deleteUser")
	public String deletePatient(@RequestParam String mailAddress) throws InterruptedException, ExecutionException
	{
		return userService.deleteUser(mailAddress);
	}
}
