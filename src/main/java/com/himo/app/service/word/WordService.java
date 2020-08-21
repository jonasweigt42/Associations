package com.himo.app.service.word;

import java.util.List;

import com.himo.app.entity.word.Word;
import com.himo.app.service.AssociationService;

public interface WordService extends AssociationService<Word>
{
	List<String> getAllNames();
	
	List<String> getRandomNames(int number);
}
