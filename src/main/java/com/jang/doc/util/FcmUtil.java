package com.jang.doc.util;

import java.io.FileInputStream;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FcmUtil{
	public void send_FCM(String tokenId, String title, String content) {
		System.out.println("Test5 fcm~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		try {
			//내 json파일 경로  
			//E:\\정현문\\Android_Web2\\src\\main\\webapp\\resources\\fcm\\
			FileInputStream refreshToken = new FileInputStream("C:\\Users\\Very\\workspace\\Android_Web2\\src\\main\\webapp\\resources\\fcm\\smartpost-4a809-firebase-adminsdk-h1awd-5f27ecce1e.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://smartpost-4a809.firebaseio.com")
					.build();
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			String registrationToken = tokenId;
			
			Message msg = Message.builder()
					.setAndroidConfig(AndroidConfig.builder()
							.setTtl(3600 * 1000)
							.setPriority(AndroidConfig.Priority.NORMAL)
							.setNotification(AndroidNotification.builder()
									.setTitle(title)
									.setBody(content)
									.setIcon("stock_ticker_update")
									.setColor("#f45342")
									.build())
							.build())
							.setToken(registrationToken)
							.build();
			
			//메세지 FirebaseMessaging으로 보내기
			String response = FirebaseMessaging.getInstance().send(msg);
			//결과 추렭
			System.out.println("Success message: " + response);
							
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}