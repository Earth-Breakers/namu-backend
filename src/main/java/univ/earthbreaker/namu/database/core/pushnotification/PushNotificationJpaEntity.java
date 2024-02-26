package univ.earthbreaker.namu.database.core.pushnotification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotification;

@Entity
@Table(name = "push_notification")
public class PushNotificationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private Boolean enable;

	@Column(nullable = false)
	private Long memberNo;

	protected PushNotificationJpaEntity() {
	}

	private PushNotificationJpaEntity(String token, Boolean enable, Long memberNo) {
		this.token = token;
		this.enable = enable;
		this.memberNo = memberNo;
	}

	static PushNotificationJpaEntity initialize(Long memberNo, String pushNotificationToken) {
		return new PushNotificationJpaEntity(pushNotificationToken, true, memberNo);
	}

	PushNotification toPushNotification() {
		return new PushNotification(no, memberNo, token, enable);
	}
}
