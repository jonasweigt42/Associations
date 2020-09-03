package com.think.app.entity.user;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{

	@Autowired
	private UserService userService;

	@GetMapping("/getUser")
	public User getUser(@RequestParam String mailAddress) throws InterruptedException, ExecutionException
	{
		return userService.getUserByMailAddress(mailAddress);
	}

	@PostMapping("/createUser")
	public void createUser(@RequestBody User user) throws InterruptedException, ExecutionException
	{
		userService.save(user);
	}

	@PutMapping("/updateUser")
	public String updateUser(@RequestBody User user) throws InterruptedException, ExecutionException
	{
		return userService.update(user);
	}

	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam String mailAddress) throws InterruptedException, ExecutionException
	{
		return userService.delete(mailAddress);
	}
}
