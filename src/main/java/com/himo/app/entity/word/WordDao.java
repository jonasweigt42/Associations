package com.himo.app.entity.word;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.himo.app.entity.GenericDao;

@Repository
public class WordDao extends GenericDao<Word>
{

	@PostConstruct
	public void init()
	{
		setClazz(Word.class);
	}
}
