package univ.earthbreaker.namu.core.domain.pushnotification;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationRepository {

	@Nullable PushNotification findOrNull(long memberNo);

	void modify(PushNotification pushNotification);

	void register(long memberNo, String pushNotificationToken);

	@NotNull List<PushNotification> findAll();

	@NotNull List<PushNotification> findAll(FriendsQuery friends);
}
