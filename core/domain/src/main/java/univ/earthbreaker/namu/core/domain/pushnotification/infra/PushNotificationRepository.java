package univ.earthbreaker.namu.core.domain.pushnotification.infra;

import java.util.List;

import univ.earthbreaker.namu.core.domain.pushnotification.PushNotification;

public interface PushNotificationRepository {

	PushNotification findOrNull(long memberNo);

	void modify(PushNotification pushNotification);

	void register(long memberNo, String pushNotificationToken);

	List<PushNotification> findAll();

	List<PushNotification> findAll(FriendsQuery friends);
}
