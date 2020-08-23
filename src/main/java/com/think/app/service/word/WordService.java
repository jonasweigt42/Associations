package com.think.app.service.word;

import java.util.List;

import com.think.app.entity.word.Word;
import com.think.app.service.Service;

public interface WordService extends Service<Word>
{
	List<String> getAllNames();
	
	List<String> getRandomWords(int number);
}
