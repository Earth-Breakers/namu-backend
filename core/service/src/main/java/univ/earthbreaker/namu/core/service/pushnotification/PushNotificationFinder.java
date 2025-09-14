package univ.earthbreaker.namu.core.service.pushnotification;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.PushNotification;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationNotFoundException;
import univ.earthbreaker.namu.core.domain.pushnotification.infra.FriendsQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.infra.PushNotificationRepository;

@Component
public class PushNotificationFinder {

	private final PushNotificationRepository pushNotificationRepository;

	PushNotificationFinder(PushNotificationRepository pushNotificationRepository) {
		this.pushNotificationRepository = pushNotificationRepository;
	}

	PushNotification find(long memberNo) {
		PushNotification pushNotification = pushNotificationRepository.findOrNull(memberNo);
		if (pushNotification == null) {
			throw PushNotificationNotFoundException.notFount();
		}
		return pushNotification;
	}

	List<PushNotification> findAllEnable() {
		return pushNotificationRepository.findAll()
			.stream()
			.filter(PushNotification::isEnable)
			.toList();
	}

	List<PushNotification> findFriendsEnable(FriendsQuery friends) {
		return pushNotificationRepository.findAll(friends)
			.stream()
			.filter(PushNotification::isEnable)
			.toList();
	}
}
