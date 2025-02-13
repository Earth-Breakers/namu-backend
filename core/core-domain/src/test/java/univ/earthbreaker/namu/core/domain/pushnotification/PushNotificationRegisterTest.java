package univ.earthbreaker.namu.core.domain.pushnotification;

import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.PUSH_TOKEN;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PushNotificationRegisterTest {

	private @Mock PushNotificationRepository pushNotificationRepository;
	private @InjectMocks PushNotificationRegister pushNotificationRegister;

	@DisplayName("팔로우할 주체 회원 번호와 팔로우 할 상대의 회원 번호를 받아 등록한다")
	@Test
	void register() {
		// when
		pushNotificationRegister.register(MEMBER_NO, PUSH_TOKEN);

		// then
		Mockito.verify(pushNotificationRepository).register(MEMBER_NO, PUSH_TOKEN);
	}
}
