package com.himo.app.service.word;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himo.app.entity.word.Word;
import com.himo.app.entity.word.WordDao;

@Service
public class WordImpl implements WordService
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
	public List<String> getAllNames()
	{
		return findAll().stream().map(word -> word.getName()).collect(Collectors.toList());
	}

	@Override
	public void delete(Word word)
	{
		dao.delete(word);
	}

	@Override
	public List<Word> getRandomEntries(int number)
	{
		return dao.getRandomEntries(number);
	}

	@Override
	public List<String> getRandomNames(int number)
	{
		return getRandomEntries(number).stream().map(word -> word.getName()).collect(Collectors.toList());
	}

}