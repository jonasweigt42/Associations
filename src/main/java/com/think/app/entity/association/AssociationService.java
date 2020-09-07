package com.think.app.entity.association;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.think.app.database.FireBaseInitialze;

@Service
public class AssociationService
{

	@Autowired
	private FireBaseInitialze fireBaseInitialize;

	@Autowired
	private Logger logger;
	
	public static final String COL_NAME = "association";

	public void save(Association association)
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();

		dbFirestore.collection(COL_NAME).document(Integer.toString(association.hashCode())).set(association);
	}

	public List<Association> getAssociationsByMailAddress(String mailaddress) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		Query query = dbFirestore.collection(COL_NAME).whereEqualTo("userMailAddress", mailaddress);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<Association> result = new ArrayList<>();
		
		for(DocumentSnapshot document : querySnapshot.get().getDocuments())
		{
			if(document.exists())
			{
				result.add(document.toObject(Association.class));
			}
		}
		return result;
	}

	public void delete(int hashCode) throws InterruptedException, ExecutionException
	{
		Firestore dbFirestore = fireBaseInitialize.getFirestore();
		ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(Integer.toString(hashCode)).delete();
		logger.info("Association with hashCode {} has been deleted at {}", hashCode, writeResult.get().getUpdateTime());
	}
}
