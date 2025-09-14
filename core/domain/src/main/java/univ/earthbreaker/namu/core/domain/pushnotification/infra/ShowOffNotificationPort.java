package univ.earthbreaker.namu.core.domain.pushnotification.infra;

import java.util.List;

public interface ShowOffNotificationPort {

	void sendShowOffMessage(PushNotificationSourceCommand sourceCommand);

	record PushNotificationSourceCommand(
		String nickname,
		String characterName,
		String messageBody,
		List<String> notificationTokens
	) {
		public PushNotificationSourceCommand changeToFailureTokens(List<String> failureTokens) {
			return new PushNotificationSourceCommand(nickname, characterName, messageBody, failureTokens);
		}
	}
}
