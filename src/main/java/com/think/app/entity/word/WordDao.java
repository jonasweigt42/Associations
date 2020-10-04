package com.think.app.entity.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.think.app.entity.GenericDao;

@Repository
public class WordDao extends GenericDao<Word>
{

	@PostConstruct
	public void init()
	{
		setClazz(Word.class);
	}
	
	@Transactional
	public List<Word> getRandomEntries(int number)
	{
		List<Word> list = new ArrayList<>(number);
		int countedObjects = count();
		if(countedObjects <= number)
		{
			return findAll();
		}
		prepareListWithRandomElements(number, list, countedObjects);
		return list;
	}

	private void prepareListWithRandomElements(int number, List<Word> list, int countedObjects)
	{
		Random random = new Random();
		while (list.size() != number)
		{
			int randomNumber = random.nextInt(countedObjects);
			Query selectQuery = entityManager.createQuery("SELECT w FROM " + Word.class.getName() + " w");
			selectQuery.setFirstResult(randomNumber);
			selectQuery.setMaxResults(1);
			Word result = (Word) selectQuery.getSingleResult();
			if (!list.contains(result))
			{
				list.add(result);
			}
		}
	}
	
	//TODO fix
	@SuppressWarnings("unchecked")
	@Transactional
	public Word findByNameAndLanguage(String name, String language)
	{
		List<Word> words = entityManager.createQuery("SELECT w FROM " + Word.class.getName() + " w WHERE w.name = "
				+ name + " AND w.language = " + language).getResultList();
		if(!words.isEmpty())
		{
			return words.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Word findById(int id)
	{
		List<Word> words = entityManager.createQuery("SELECT w FROM " + Word.class.getName() + " w WHERE w.id = "
				+ id).getResultList();
		if(!words.isEmpty())
		{
			return words.get(0);
		}
		return null;
	}

}