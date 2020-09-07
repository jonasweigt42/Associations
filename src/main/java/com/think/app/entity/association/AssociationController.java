package com.think.app.entity.association;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssociationController
{
	@Autowired
	private AssociationService associationService;

	@GetMapping("/getAssociation")
	public List<Association> getAssociationsByMailAddress(@RequestParam String mailAddress) throws InterruptedException, ExecutionException
	{
		return associationService.getAssociationsByMailAddress(mailAddress);
	}

	@PostMapping("/createAssociation")
	public void createAssociation(@RequestBody Association association)
	{
		associationService.save(association);
	}

	@DeleteMapping("/deleteAssociation")
	public void deleteAssociation(@RequestParam int hashCode) throws InterruptedException, ExecutionException
	{
		associationService.delete(hashCode);
	}
}
