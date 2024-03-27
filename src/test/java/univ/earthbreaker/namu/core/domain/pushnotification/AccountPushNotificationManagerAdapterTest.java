package univ.earthbreaker.namu.core.domain.pushnotification;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.PUSH_NOTIFICATION;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.PUSH_TOKEN;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountPushNotificationManagerAdapterTest {

	private @Mock PushNotificationRegister pushNotificationRegister;
	private @Mock PushNotificationFinder pushNotificationFinder;
	private @Mock PushNotificationRepository pushNotificationRepository;
	private @InjectMocks AccountPushNotificationManagerAdapter accountPushNotificationManagerAdapter;

	@DisplayName("회원의 번호와 푸시 알림 토큰값을 받아 등록한다")
	@Test
	void register() {
	    // when
		accountPushNotificationManagerAdapter.register(MEMBER_NO, PUSH_TOKEN);

	    // then
		verify(pushNotificationRegister).register(MEMBER_NO, PUSH_TOKEN);
	}

	@DisplayName("회원 번호와 푸시 알림 토큰값을 받아 토큰값이 변경되었는지 확인하고, 변경되었다면 푸시 알림 토큰값을 변경한다")
	@Test
	void run_updateIfTokenModified() {
	    // given
		when(pushNotificationFinder.find(MEMBER_NO))
			.thenReturn(PUSH_NOTIFICATION);

	    // when
		accountPushNotificationManagerAdapter.updateIfTokenModified(MEMBER_NO, "anotherToken");

	    // then
		verify(pushNotificationRepository).modify(any(PushNotification.class));
	}

	@DisplayName("회원 번호와 푸시 알림 토큰값을 받아 토큰값이 변경되었는지 확인하고, 변경되지 않았다면 푸시 알림 토큰값을 변경하지 않는다")
	@Test
	void do_not_run_updateIfTokenModified() {
		// given
		when(pushNotificationFinder.find(MEMBER_NO))
			.thenReturn(PUSH_NOTIFICATION);

		// when
		accountPushNotificationManagerAdapter.updateIfTokenModified(MEMBER_NO, PUSH_TOKEN);

		// then
		verify(pushNotificationRepository, never()).modify(any(PushNotification.class));
	}
}
