package com.think.app.entity.word;

import java.util.List;

import com.think.app.entity.Service;

public interface WordService extends Service<Word>
{
	List<String> getAllNames();
	
	List<String> getRandomWords(int number, String language);
}