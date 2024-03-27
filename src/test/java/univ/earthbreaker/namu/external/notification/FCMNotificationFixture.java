package univ.earthbreaker.namu.external.notification;

import java.util.List;

public class FCMNotificationFixture {

	static final String SUCCESS_FCM_TOKEN_1 = "123fcmToken";
	static final String SUCCESS_FCM_TOKEN_2 = "456fcmToken";
	static final String FAILURE_FCM_TOKEN = "789fcmToken";
	static final List<String> FCM_TOKENS = List.of(SUCCESS_FCM_TOKEN_1, SUCCESS_FCM_TOKEN_2, FAILURE_FCM_TOKEN);
	static final List<String> FAILURE_FCM_TOKENS = List.of(FAILURE_FCM_TOKEN, FAILURE_FCM_TOKEN, FAILURE_FCM_TOKEN);

	static final String MEMBER_NICKNAME = "nickname";
	static final String CHARACTER_NAME = "characterName";
	static final String MESSAGE_BODY = "message";
	static final PushNotificationSourceCommand NOTIFICATION_SOURCE_COMMAND = new PushNotificationSourceCommand(
		MEMBER_NICKNAME, CHARACTER_NAME, MESSAGE_BODY, FCM_TOKENS);
}
