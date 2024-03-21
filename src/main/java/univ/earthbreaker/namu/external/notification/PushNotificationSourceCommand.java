package univ.earthbreaker.namu.external.notification;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public record PushNotificationSourceCommand(
	String nickname,
	String characterName,
	String messageBody,
	List<String> notificationTokens
) {
	@NotNull PushNotificationSourceCommand changeToFailureTokens(List<String> failureTokens) {
		return new PushNotificationSourceCommand(nickname, characterName, messageBody, failureTokens);
	}
}
