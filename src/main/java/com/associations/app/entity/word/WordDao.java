package com.associations.app.entity.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.associations.app.entity.GenericDao;

@Repository
public class WordDao extends GenericDao<Word>
{

	@PostConstruct
	public void init()
	{
		setClazz(Word.class);
	}

	@Transactional
	public List<Word> getRandomWords(int number, String language)
	{
		int countedObjects = countByLanguage(language);
		if (countedObjects <= number)
		{
			return findAll();
		}
		return prepareListWithRandomElements(number, language);
	}

	private int countByLanguage(String language)
	{
		return findAll().stream().filter(w -> w.getLanguage().equals(language)).collect(Collectors.toList()).size();
	}

	private List<Word> prepareListWithRandomElements(int number, String language)
	{
		List<Word> result = new ArrayList<>();
		Random random = new Random();

		TypedQuery<Word> query = entityManager.createNamedQuery("Word.findByLanguage", Word.class);
		query.setParameter(1, language);
		List<Word> words = query.getResultList();

		while (result.size() != number)
		{
			int randomNumber = random.nextInt(words.size());
			Word wordToAdd = words.get(randomNumber);

			if (!result.contains(wordToAdd))
			{
				result.add(words.get(randomNumber));
				words.remove(wordToAdd);
			}
		}
		return result;
	}

	@Transactional
	public Word findByNameAndLanguage(String name, String language)
	{
		TypedQuery<Word> query = entityManager.createNamedQuery("Word.findbyNameAndLanguage", Word.class);
		query.setParameter(1, name);
		query.setParameter(2, language);
		List<Word> words = query.getResultList();
		if (!words.isEmpty())
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