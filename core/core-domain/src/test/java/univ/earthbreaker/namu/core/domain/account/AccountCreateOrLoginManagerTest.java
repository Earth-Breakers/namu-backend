package univ.earthbreaker.namu.core.domain.account;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static univ.earthbreaker.namu.core.domain.account.AccountFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountCreateOrLoginManagerTest {

	private @Mock AccountRepository accountRepository;
	private @Mock AccountMemberCreator accountMemberCreator;
	private @Mock AccountPushNotificationManager accountPushNotificationManager;
	private @Mock TokenManager tokenManager;
	private @InjectMocks AccountCreateOrLoginManager accountCreateOrLoginManager;

	@DisplayName("""
		회원가입 -
		socialId 를 받아, 새로운 회원이면 계정 생성 및 회원을 등록하고,
		accessToken, refreshToken, 새로운 회원 여부를 반환한다
		""")
	@Test
	void join() {
		// when
		when(accountRepository.findOrNull(SOCIAL_ID))
			.thenReturn(null);
		when(accountMemberCreator.create(SOCIAL_NICKNAME))
			.thenReturn(MEMBER_NO);
		when(tokenManager.createAccessToken(any()))
			.thenReturn(ACCESS_TOKEN);
		when(tokenManager.createRefreshToken(MEMBER_NO))
			.thenReturn(REFRESH_TOKEN);

		// then
		LoginResult loginResult = accountCreateOrLoginManager.loginOrJoin(LOGIN_COMMAND);

		// then
		assertAll(
			() -> assertThat(loginResult.accessToken()).isEqualTo(ACCESS_TOKEN),
			() -> assertThat(loginResult.refreshToken()).isEqualTo(REFRESH_TOKEN),
			() -> assertThat(loginResult.isNewMember()).isTrue(),
			() -> verify(accountRepository).create(refEq(LOGIN_COMMAND.toKakaoCommand(MEMBER_NO))),
			() -> verify(accountPushNotificationManager).register(MEMBER_NO, NOTIFICATION_TOKEN)
		);
	}

	@DisplayName("""
		로그인 -
		socialId 를 받아, 기존 회원이면 accessToken, refreshToken, 새로운 회원 여부를 반환한다
		""")
	@Test
	void login() {
		// given
		when(accountRepository.findOrNull(SOCIAL_ID))
			.thenReturn(ACCOUNT);
		when(tokenManager.createAccessToken(any()))
			.thenReturn(ACCESS_TOKEN);
		when(tokenManager.updateRefreshToken(MEMBER_NO))
			.thenReturn(REFRESH_TOKEN);

		// when
		LoginResult loginResult = accountCreateOrLoginManager.loginOrJoin(LOGIN_COMMAND);

		// then
		assertAll(
			() -> assertThat(loginResult.accessToken()).isEqualTo(ACCESS_TOKEN),
			() -> assertThat(loginResult.refreshToken()).isEqualTo(REFRESH_TOKEN),
			() -> assertThat(loginResult.isNewMember()).isFalse(),
			() -> verify(accountPushNotificationManager).updateIfTokenModified(MEMBER_NO, NOTIFICATION_TOKEN)
		);
	}
}
