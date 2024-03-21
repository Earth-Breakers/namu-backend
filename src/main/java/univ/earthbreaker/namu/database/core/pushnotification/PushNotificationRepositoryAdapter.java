package univ.earthbreaker.namu.database.core.pushnotification;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.pushnotification.FriendsQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotification;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationRepository;

@Repository
public class PushNotificationRepositoryAdapter implements PushNotificationRepository {

	private final PushNotificationJpaRepository pushNotificationJpaRepository;

	public PushNotificationRepositoryAdapter(PushNotificationJpaRepository pushNotificationJpaRepository) {
		this.pushNotificationJpaRepository = pushNotificationJpaRepository;
	}

	@Override
	public @Nullable PushNotification findOrNull(long memberNo) {
		PushNotificationJpaEntity pushNotificationJpaEntity = pushNotificationJpaRepository.findByMemberNo(memberNo);
		if (pushNotificationJpaEntity != null) {
			return pushNotificationJpaEntity.toPushNotification();
		}
		return null;
	}

	@Override
	public void modify(@NotNull PushNotification pushNotification) {
		pushNotificationJpaRepository.updatePushNotificationToken(pushNotification.getNo(), pushNotification.getToken());
	}

	@Override
	public void register(long memberNo, String pushNotificationToken) {
		pushNotificationJpaRepository.save(PushNotificationJpaEntity.initialize(memberNo, pushNotificationToken));
	}

	@Override
	public @NotNull List<PushNotification> findAll() {
		return pushNotificationJpaRepository.findAll()
			.stream()
			.map(PushNotificationJpaEntity::toPushNotification)
			.toList();
	}

	@Override
	public @NotNull List<PushNotification> findAll(@NotNull FriendsQuery friends) {
		return pushNotificationJpaRepository.findAllByMemberNoIn(friends.memberNos())
			.stream()
			.map(PushNotificationJpaEntity::toPushNotification)
			.toList();
	}
}
