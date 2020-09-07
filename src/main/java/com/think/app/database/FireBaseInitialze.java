package com.think.app.database;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.think.app.constants.TextConstants;

@Service
public class FireBaseInitialze
{

	@Autowired
	private Logger logger;

	@PostConstruct
	public void initialize()
	{
		try
		{
			InputStream serviceAccount = this.getClass().getClassLoader()
					.getResourceAsStream("./thinkconnected-ff6d2-firebase-adminsdk-y4pog-8b03ec96d8.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://thinkconnected-ff6d2.firebaseio.com").build();

			if (FirebaseApp.getApps().isEmpty())
			{
				FirebaseApp.initializeApp(options);
			}
		} catch (Exception e)
		{
			logger.error(TextConstants.COULD_NOT_CONNECT_TO_FIREBASE, e);
		}
	}

	public Firestore getFirestore()
	{
		return FirestoreClient.getFirestore();
	}
}
