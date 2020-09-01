package com.think.app.database;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

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
			FileInputStream serviceAccount = new FileInputStream(
					"thinkconnected-ff6d2-firebase-adminsdk-y4pog-8b03ec96d8.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://thinkconnected-ff6d2.firebaseio.com").build();

			FirebaseApp.initializeApp(options);
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}

	}
}
