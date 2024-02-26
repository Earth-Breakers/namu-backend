package univ.earthbreaker.namu.core.domain.pushnotification;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationFinder {

	private final PushNotificationRepository pushNotificationRepository;

	public PushNotificationFinder(PushNotificationRepository pushNotificationRepository) {
		this.pushNotificationRepository = pushNotificationRepository;
	}

	public @NotNull PushNotification find(Long memberNo) {
		PushNotification pushNotification = pushNotificationRepository.findOrNull(memberNo);
		if (pushNotification == null) {
			throw PushNotificationNotFoundException.notFount();
		}
		return pushNotification;
	}
}
