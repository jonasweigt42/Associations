package com.think.app.service.word;

import java.util.List;

import com.think.app.entity.word.Word;
import com.think.app.service.AssociationService;

public interface WordService extends AssociationService<Word>
{
	List<String> getAllNames();
	
	List<String> getRandomNames(int number);
}
