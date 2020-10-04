package com.think.app.entity.word;

import java.util.List;

import com.think.app.entity.Service;

public interface WordService extends Service<Word>
{
	List<Word> getRandomWords(int number, String language);
	
	Word findByNameAndLanguage(String name, String language);
	
	Word findById(int id);
	
}