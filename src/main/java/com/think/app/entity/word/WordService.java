package com.think.app.entity.word;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.think.app.database.FireBaseInitialze;

@Service
public class WordService
{

	@Autowired
	private FireBaseInitialze fireBaseInitialize;

	@Autowired
	private Logger logger;

	public static final String COL_NAME = "word";
	
	public void saveWord(Word word) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();

		Word alreadyExistingWord = getWord(word.getName());
		if (alreadyExistingWord != null)
		{
			logger.info(word.getName() + " already exists");
		}

		dbFirestore.collection(COL_NAME).document(word.getName()).set(word);
	}
	
//	//TODO find words, test if document returns a random value
//	public List<Word> getWords(int count) throws InterruptedException, ExecutionException
//	{
//		Firestore dbFirestore = fireBaseInitialize.getFirestore();
//		DocumentReference documentReference = dbFirestore.collection(COL_NAME).get;
//		ApiFuture<DocumentSnapshot> future = documentReference.get();
//
//		DocumentSnapshot document = future.get();
//
//		if (document.exists())
//		{
//			return document.toObject(Word.class);
//		} else
//		{
//			return null;
//		}
//	}

	public Word getWord(String word) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(word);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();

		if (document.exists())
		{
			return document.toObject(Word.class);
		} else
		{
			return null;
		}
	}

	public String deleteWord(String wordName) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(wordName).delete();
		return "Document with word " + wordName + " has been deleted:" + writeResult.get().getUpdateTime().toString();
	}
}
