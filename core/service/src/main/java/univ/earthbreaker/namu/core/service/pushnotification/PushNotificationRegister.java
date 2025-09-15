package univ.earthbreaker.namu.core.service.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.infra.PushNotificationRepository;

@Component
public class PushNotificationRegister {

	private final PushNotificationRepository pushNotificationRepository;

	public PushNotificationRegister(PushNotificationRepository pushNotificationRepository) {
		this.pushNotificationRepository = pushNotificationRepository;
	}

	public void register(Long memberNo, String pushNotificationToken) {
		pushNotificationRepository.register(memberNo, pushNotificationToken);
	}
}
