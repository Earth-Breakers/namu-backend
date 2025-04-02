package univ.earthbreaker.namu.core.domain.pushnotification;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.PUSH_NOTIFICATION;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PushNotificationTest {

	@DisplayName("푸시 알림 토큰이 같은 값이면 false 를 반환하고, 다른 값이면 true 를 반환한다")
	@ParameterizedTest
	@CsvSource({"token, false", "anotherToken, true"})
	void isNotSame(String token, boolean expect) {
		// when
		boolean actual = PUSH_NOTIFICATION.isNotSame(token);

		// then
		assertThat(actual).isEqualTo(expect);
	}

	@DisplayName("새로운 토큰 값을 받아 푸시 알림의 토큰 값을 수정하고, 수정된 푸시 알림 객체를 반환한다")
	@Test
	void modifyToken() {
	    // given
		PushNotification pushNotification = PUSH_NOTIFICATION;
		String newToken = "newToken";

	    // when
		PushNotification actual = pushNotification.modifyToken(newToken);

		// then
		assertAll(
			() -> assertThat(actual.getNo()).isEqualTo(pushNotification.getNo()),
			() -> assertThat(actual.getToken()).isEqualTo(newToken)
		);
	}
}
