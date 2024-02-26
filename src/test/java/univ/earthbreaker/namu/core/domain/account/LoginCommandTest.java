package univ.earthbreaker.namu.core.domain.account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LoginCommandTest {

	private static final String BLANK = "";

	@DisplayName("socialId, socialNickname, notificationToken 중 하나라도 공백이 들어가면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideSocialIdAndSocialNicknameAndNotificationToken")
	void fail_LoginCommandBuilder(String socialId, String socialNickname, String notificationToken) {
		// when, then
		assertThatThrownBy(() -> LoginCommand.builder()
			.socialId(socialId)
			.socialNickname(socialNickname)
			.notificationToken(notificationToken)
			.build()
		)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("socialId, socialNickname 과 notificationToken 은 공백이 될 수 없습니다");
	}

	private static @NotNull Stream<Arguments> provideSocialIdAndSocialNicknameAndNotificationToken() {
		return Stream.of(
			Arguments.of(BLANK, "socialNickname", "notificationToken"),
			Arguments.of("socialId", BLANK, "notificationToken"),
			Arguments.of("socialId", "socialNickname", BLANK),
			Arguments.of(BLANK, BLANK, "notificationToken"),
			Arguments.of("socialId", BLANK, BLANK),
			Arguments.of(BLANK, BLANK, BLANK)
		);
	}

	@DisplayName("카카오 계정 생성을 위한 command 객체를 생성한다")
	@Test
	void toKakaoCommand() {
	    // given
		LoginCommand loginCommand = LoginCommand.builder()
			.socialId("socialId")
			.socialNickname("socialNickname")
			.notificationToken("notificationToken")
			.build();

		// when
		AccountCreateCommand accountCreateCommand = loginCommand.toKakaoCommand(1L);

		// then
		assertAll(
			() -> assertThat(accountCreateCommand).isInstanceOf(KakaoAccountCreateCommand.class),
			() -> assertThat(accountCreateCommand.getSocialId()).isEqualTo("socialId"),
			() -> assertThat(accountCreateCommand.getMemberNo()).isEqualTo(1L),
			() -> assertThat(accountCreateCommand.getSocialType()).isEqualTo(SocialType.KAKAO)
		);
	}
}
