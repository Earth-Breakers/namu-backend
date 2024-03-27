package univ.earthbreaker.namu.external.notification;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FcmConfig {

	private static final String FIREBASE_APP_NAME = "namu";

	private final String firebaseConfigPath;

	public FcmConfig(@Value("${firebase.fcm.config.path}") String firebaseConfigPath) {
		this.firebaseConfigPath = firebaseConfigPath;
	}

	@Bean
	public FirebaseApp firebaseApp() throws IOException {
		ClassPathResource firebaseResource = new ClassPathResource(firebaseConfigPath);
		GoogleCredentials credentials = GoogleCredentials.fromStream(firebaseResource.getInputStream());
		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(credentials)
			.build();
		if (FirebaseApp.getApps().isEmpty()) {
			return FirebaseApp.initializeApp(options, FIREBASE_APP_NAME);
		}
		return FirebaseApp.getInstance(FIREBASE_APP_NAME);
	}

	@Bean
	public FirebaseMessaging firebaseMessaging() throws IOException {
		return FirebaseMessaging.getInstance(firebaseApp());
	}
}
