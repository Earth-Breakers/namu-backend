package univ.earthbreaker.namu.core.domain.pushnotification;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationFinder {

	private final PushNotificationRepository pushNotificationRepository;

	PushNotificationFinder(PushNotificationRepository pushNotificationRepository) {
		this.pushNotificationRepository = pushNotificationRepository;
	}

	@NotNull PushNotification find(long memberNo) {
		PushNotification pushNotification = pushNotificationRepository.findOrNull(memberNo);
		if (pushNotification == null) {
			throw PushNotificationNotFoundException.notFount();
		}
		return pushNotification;
	}

	@NotNull List<PushNotification> findAllEnable() {
		return pushNotificationRepository.findAll()
			.stream()
			.filter(PushNotification::isEnable)
			.toList();
	}

	@NotNull List<PushNotification> findFriendsEnable(FriendsQuery friends) {
		return pushNotificationRepository.findAll(friends)
			.stream()
			.filter(PushNotification::isEnable)
			.toList();
	}
}
