package univ.earthbreaker.namu.core.domain.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.account.AccountPushNotificationManager;

@Component
public class AccountPushNotificationManagerAdapter implements AccountPushNotificationManager {

	private final PushNotificationRegister pushNotificationRegister;
	private final PushNotificationFinder pushNotificationFinder;
	private final PushNotificationRepository pushNotificationRepository;

	public AccountPushNotificationManagerAdapter(
		PushNotificationRegister pushNotificationRegister,
		PushNotificationFinder pushNotificationFinder,
		PushNotificationRepository pushNotificationRepository
	) {
		this.pushNotificationRegister = pushNotificationRegister;
		this.pushNotificationFinder = pushNotificationFinder;
		this.pushNotificationRepository = pushNotificationRepository;
	}

	@Override
	public void register(Long memberNo, String pushNotificationToken) {
		pushNotificationRegister.register(memberNo, pushNotificationToken);
	}

	@Override
	public void updateIfTokenModified(Long memberNo, String pushNotificationToken) {
		PushNotification pushNotification = pushNotificationFinder.find(memberNo);
		if (pushNotification.isNotSame(pushNotificationToken)) {
			PushNotification modifiedPushNotification = pushNotification.modifyToken(pushNotificationToken);
			pushNotificationRepository.modify(modifiedPushNotification);
		}
	}
}
