package univ.earthbreaker.namu.core.domain.pushnotification;

import java.util.List;

public class PushNotificationFixture {

	public static final long MEMBER_NO = 1L;
	public static final long TARGET_MEMBER_NO_1 = 2L;
	public static final long TARGET_MEMBER_NO_2 = 3L;
	public static final String PUSH_TOKEN = "token";
	public static final PushNotification PUSH_NOTIFICATION = new PushNotification(1, MEMBER_NO, PUSH_TOKEN, true);
	public static final PushNotification TARGET_PUSH_NOTIFICATION_ENABLE
		= new PushNotification(2, TARGET_MEMBER_NO_1, "", true);
	public static final PushNotification TARGET_PUSH_NOTIFICATION_UNABLE
		= new PushNotification(3, TARGET_MEMBER_NO_2, "", false);
	public static final PushNotificationConstructResult PUSH_NOTIFICATION_CONSTRUCT_RESULT
		= new PushNotificationConstructResult("nickname", "characterName", List.of(PUSH_TOKEN));
}
