package univ.earthbreaker.namu.core.domain.pushnotification;

import java.util.List;

public record PushNotificationConstructResult(
	String nickname,
	String characterName,
	List<String> notificationTokens
) {
	public static PushNotificationConstructResult of(
		MemberQuery memberQuery,
		CharacterQuery characterQuery,
		List<PushNotification> pushNotifications
	) {
		return new PushNotificationConstructResult(
			memberQuery.nickname(),
			characterQuery.name(),
			pushNotifications.stream()
				.map(PushNotification::getToken)
				.toList()
		);
	}
}
