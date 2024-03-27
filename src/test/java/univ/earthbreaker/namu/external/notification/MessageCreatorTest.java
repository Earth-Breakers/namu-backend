package univ.earthbreaker.namu.external.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static univ.earthbreaker.namu.external.notification.FCMNotificationFixture.NOTIFICATION_SOURCE_COMMAND;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.firebase.messaging.Message;

class MessageCreatorTest {

	@DisplayName("""
		푸시 알림 정보
		(보내는 회원의 별명, 자랑할 캐릭터 이름, 자랑 메시지, 메시지를 전송받을 회원들의 푸시 알림 토큰값)
		를 인자로 받아 Message 들을 생성해 반환한다""")
	@Test
	void createMessages() {
	    // when
		List<Message> messages = MessageCreator.createMessages(NOTIFICATION_SOURCE_COMMAND);

		// then
		assertThat(messages).isNotNull().hasSize(3);
	}
}
