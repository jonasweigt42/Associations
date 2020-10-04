package com.think.app.entity.word;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService
{

	@Autowired
	private WordDao dao;

	@Override
	public List<Word> findAll()
	{
		return dao.findAll();
	}

	@Override
	public int count()
	{
		return dao.count();
	}

	@Override
	public void update(Word word)
	{
		dao.update(word);
	}

	@Override
	public void save(Word newWord)
	{
		dao.save(newWord);
	}

	@Override
	public void delete(Word word)
	{
		dao.delete(word);
	}

	@Override
	public List<Word> getRandomWords(int number, String language)
	{
		return dao.getRandomEntries(number).stream().filter(word -> word.getLanguage().equals(language))
				.collect(Collectors.toList());
	}

	@Override
	public Word findById(int id)
	{
		return dao.findById(id);
	}

	@Override
	public Word findByNameAndLanguage(String name, String language)
	{
		return dao.findByNameAndLanguage(name, language);
	}

}