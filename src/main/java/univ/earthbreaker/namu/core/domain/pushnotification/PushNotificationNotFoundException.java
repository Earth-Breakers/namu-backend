package univ.earthbreaker.namu.core.domain.pushnotification;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class PushNotificationNotFoundException extends NotFoundException {

	private static final String PUSH_NOTIFICATION_DOMAIN_NAME = "푸시 알림 토큰";

	private PushNotificationNotFoundException(String domainName) {
		super(domainName);
	}

	static @NotNull PushNotificationNotFoundException notFount() {
		return new PushNotificationNotFoundException(PUSH_NOTIFICATION_DOMAIN_NAME);
	}
}
