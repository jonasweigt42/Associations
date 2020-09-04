package com.think.app.entity.user;

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
public class UserService
{
	@Autowired
	private FireBaseInitialze fireBaseInitialize;

	@Autowired
	private Logger logger;

	public static final String COL_NAME = "user";

	public void save(User user) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();

		User alreadyExistingUser = getUserByMailAddress(user.getMailAddress());
		if (alreadyExistingUser != null)
		{
			logger.info(user.getMailAddress() + " already exists");
		}

		dbFirestore.collection(COL_NAME).document(user.getMailAddress()).set(user);
	}

	public User getUserByMailAddress(String mailaddress) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(mailaddress);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();

		if (document.exists())
		{
			return document.toObject(User.class);
		} else
		{
			return null;
		}
	}

	public String update(User user) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getMailAddress())
				.set(user);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public String delete(String mailaddress) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(mailaddress).delete();
		return "Document with user " + mailaddress + " has been deleted" + writeResult.get().getUpdateTime().toString();
	}
}