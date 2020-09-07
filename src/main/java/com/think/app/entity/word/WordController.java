package com.think.app.entity.word;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordController
{

	@Autowired
	private WordService wordService;

	@GetMapping("/getWord")
	public Word getWord(@RequestParam String wordName) throws InterruptedException, ExecutionException
	{
		return wordService.getWord(wordName);
	}

	@PostMapping("/creatWord")
	public void createWord(@RequestBody Word word) throws InterruptedException, ExecutionException
	{
		wordService.saveWord(word);
	}

	@DeleteMapping("/deleteWord")
	public void deleteWord(@RequestParam String wordName) throws InterruptedException, ExecutionException
	{
		wordService.deleteWord(wordName);
	}
}
