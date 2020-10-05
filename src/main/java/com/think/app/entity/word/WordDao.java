package com.think.app.entity.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
	
	@Transactional
	public Word findByNameAndLanguage(String name, String language)
	{
		TypedQuery<Word> query = entityManager.createNamedQuery("Word.findbyNameAndLanguage", Word.class);
		query.setParameter(1, name);
		query.setParameter(2, language);
		List<Word> words = query.getResultList();
		if(!words.isEmpty())
		{
			return words.get(0);
		}
		return null;
	}
	
	@Transactional
	public Word findById(int id)
	{
		TypedQuery<Word> query = entityManager.createNamedQuery("Word.findbyId", Word.class);
		query.setParameter(1, id);
		return query.getSingleResult();
	}

}