package univ.earthbreaker.namu.core.domain.pushnotification;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationRepository {

	@Nullable PushNotification findOrNull(Long memberNo);

	void modify(PushNotification pushNotification);

	void register(Long memberNo, String pushNotificationToken);
}
