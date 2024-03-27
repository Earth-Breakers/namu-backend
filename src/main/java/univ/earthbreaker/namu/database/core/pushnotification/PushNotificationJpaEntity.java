package univ.earthbreaker.namu.database.core.pushnotification;

import org.jetbrains.annotations.NotNull;

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

	PushNotification toPushNotification() {
		return new PushNotification(no, memberNo, token, enable);
	}

	/**
	 * 현재 ANDROID 를 기본 값으로 초기화함.
	 * 추후 디바이스 종류가 다양해진다면 해당 메서드 분리 가능.
	 */
	static @NotNull PushNotificationJpaEntity initialize(Long memberNo, String pushNotificationToken) {
		return new PushNotificationJpaEntity(pushNotificationToken, true, memberNo);
	}
}
