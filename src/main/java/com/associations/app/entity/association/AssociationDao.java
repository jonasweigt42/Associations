package com.associations.app.entity.association;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.associations.app.entity.GenericDao;
import com.associations.app.entity.word.Word;

@Repository
public class AssociationDao extends GenericDao<Association>
{
	@PostConstruct
	public void init()
	{
		setClazz(Association.class);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Association> findByUserId(int userId)
	{
		return entityManager.createQuery("SELECT a FROM " + Association.class.getName() + " a WHERE a.userId = "
				+ userId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Association> findByWordId(int wordId)
	{
		return entityManager.createQuery("SELECT a FROM " + Association.class.getName() + " a WHERE a.wordId = "
				+ wordId).getResultList();
	}
	
	
	@Transactional
	public Association findByKey(int userId, int wordId, String association)
	{
		TypedQuery<Association> query = entityManager.createNamedQuery("Association.findbyKey", Association.class);
		query.setParameter(1, userId);
		query.setParameter(2, wordId);
		query.setParameter(3, association);
		List<Association> result = query.getResultList();
		if(!result.isEmpty())
		{
			return result.get(0);
		}
		return null;
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
}