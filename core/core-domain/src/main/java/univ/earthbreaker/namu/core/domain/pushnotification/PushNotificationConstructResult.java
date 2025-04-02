package univ.earthbreaker.namu.core.domain.pushnotification;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public record PushNotificationConstructResult(
	String nickname,
	String characterName,
	List<String> notificationTokens
) {
	static @NotNull PushNotificationConstructResult of(
		@NotNull MemberQuery memberQuery,
		@NotNull CharacterQuery characterQuery,
		@NotNull List<PushNotification> pushNotifications
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
