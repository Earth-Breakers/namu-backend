package univ.earthbreaker.namu.core.domain.pushnotification;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.PUSH_NOTIFICATION;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_MEMBER_NO_1;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_MEMBER_NO_2;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_PUSH_NOTIFICATION_ENABLE;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_PUSH_NOTIFICATION_UNABLE;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PushNotificationFinderTest {

	private @Mock PushNotificationRepository pushNotificationRepository;
	private @InjectMocks PushNotificationFinder pushNotificationFinder;

	@DisplayName("회원 번호를 받아 해당 회원의 푸시 알림 정보를 반환한다")
	@Test
	void success_find() {
		// given
		when(pushNotificationRepository.findOrNull(MEMBER_NO))
			.thenReturn(PUSH_NOTIFICATION);

		// when
		PushNotification actual = pushNotificationFinder.find(MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
		assertThat(actual).isEqualTo(PUSH_NOTIFICATION);
	}

	@DisplayName("회원 번호를 받아 해당 회원의 푸시 알림 정보를 찾고, 존재하지 않으면 예외를 발생시킨다")
	@Test
	void fail_find() {
		// given
		when(pushNotificationRepository.findOrNull(MEMBER_NO))
			.thenReturn(null);

		// when, then
		assertThatThrownBy(() -> pushNotificationFinder.find(MEMBER_NO))
			.isInstanceOf(PushNotificationNotFoundException.class)
			.hasMessage(PushNotificationNotFoundException.notFount().getMessage());
	}

	@DisplayName("모든 회원의 푸시 알림 정보를 찾고, 알림 전송을 허용해둔 푸시 알림들만 반환한다")
	@Test
	void findAllEnable() {
		// given
		when(pushNotificationRepository.findAll())
			.thenReturn(List.of(
				TARGET_PUSH_NOTIFICATION_ENABLE,
				TARGET_PUSH_NOTIFICATION_UNABLE
			));

		// when
		List<PushNotification> actual = pushNotificationFinder.findAllEnable();

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual).asList().hasSize(1),
			() -> assertThat(actual).asList().contains(TARGET_PUSH_NOTIFICATION_ENABLE),
			() -> assertThat(actual).asList().doesNotContain(TARGET_PUSH_NOTIFICATION_UNABLE)
		);
	}

	@DisplayName("회원의 친구 정보를 받아, 친구들의 푸시 알림 정보를 찾고, 알림 전송으 허용해둔 푸시 알림들만 반환한다")
	@Test
	void findFriendsEnable() {
		// given
		FriendsQuery friends = new FriendsQuery(List.of(MEMBER_NO, TARGET_MEMBER_NO_1, TARGET_MEMBER_NO_2));
		when(pushNotificationRepository.findAll(friends))
			.thenReturn(List.of(
				TARGET_PUSH_NOTIFICATION_ENABLE,
				TARGET_PUSH_NOTIFICATION_UNABLE
			));

		// when
		List<PushNotification> actual = pushNotificationFinder.findFriendsEnable(friends);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual).asList().hasSize(1),
			() -> assertThat(actual).asList().contains(TARGET_PUSH_NOTIFICATION_ENABLE),
			() -> assertThat(actual).asList().doesNotContain(TARGET_PUSH_NOTIFICATION_UNABLE)
		);
	}
}
