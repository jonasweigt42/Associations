package com.think.app.service.user;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.think.app.entity.user.User;

@Service
public class UserService
{
	public static final String COL_NAME = "user";

	public String saveUser(User user) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getMailAddress())
				.set(user);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public User getUser(String mailaddress) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(mailaddress);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();

		User patient = null;

		if (document.exists())
		{
			patient = document.toObject(User.class);
			return patient;
		} else
		{
			return null;
		}
	}

	public String updateUser(User user) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getMailAddress())
				.set(user);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public String deleteUser(String mailaddress) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(mailaddress).delete();
		return "Document with user " + mailaddress + " has been deleted" + writeResult.get().getUpdateTime().toString();
	}
}
