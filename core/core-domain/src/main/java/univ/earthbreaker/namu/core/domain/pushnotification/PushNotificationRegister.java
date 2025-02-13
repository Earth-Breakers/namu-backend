package univ.earthbreaker.namu.core.domain.pushnotification;

import org.springframework.stereotype.Component;

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
