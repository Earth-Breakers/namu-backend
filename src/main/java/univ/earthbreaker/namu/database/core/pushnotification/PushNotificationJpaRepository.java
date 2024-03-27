package univ.earthbreaker.namu.database.core.pushnotification;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PushNotificationJpaRepository extends JpaRepository<PushNotificationJpaEntity, Long> {

	@Nullable PushNotificationJpaEntity findByMemberNo(Long memberNo);

	@Modifying
	@Query("UPDATE PushNotificationJpaEntity pn SET pn.token = :token WHERE pn.no = :no")
	void updatePushNotificationToken(Long no, String token);

	List<PushNotificationJpaEntity> findAllByMemberNoIn(List<Long> memberNos);
}
