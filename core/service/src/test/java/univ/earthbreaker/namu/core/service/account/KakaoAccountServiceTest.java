package univ.earthbreaker.namu.core.domain.account;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static univ.earthbreaker.namu.core.domain.account.AccountFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.character.InitCurrentCharacterEvent;
import univ.earthbreaker.namu.event.point.InitEnergyPointEvent;

@ExtendWith(MockitoExtension.class)
class KakaoAccountServiceTest {

	private @Mock AccountCreateOrLoginManager accountCreateOrLoginManager;
	private @Mock EventPublisher eventPublisher;
	private @InjectMocks KakaoAccountService kakaoAccountService;

	@DisplayName("""
		로그인/회원가입 결과 회원가입에 해당하면,
		회원의 초기 캐릭터를 지급하는 이벤트와
		회원의 초기 에너지 포인트를 지급하는 이벤트를 발행하고
		회원가입 결과를 반환한다""")
	@Test
	void join() {
	    // given
		LoginCommand loginCommand = LOGIN_COMMAND;
		when(accountCreateOrLoginManager.loginOrJoin(loginCommand))
			.thenReturn(NEW_MEMBER_RESULT);

	    // when
		LoginResult actual = kakaoAccountService.loginOrJoin(loginCommand);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull().isEqualTo(NEW_MEMBER_RESULT),
			() -> verify(eventPublisher).publish(any(InitCurrentCharacterEvent.class)),
			() -> verify(eventPublisher).publish(any(InitEnergyPointEvent.class))
		);
	}

	@DisplayName("로그인/회원가입 결과 로그인에 해당하면, 어떤 이벤트도 발행하지 않고 로그인 결과를 반환한다")
	@Test
	void login() {
		// given
		LoginCommand loginCommand = LOGIN_COMMAND;
		when(accountCreateOrLoginManager.loginOrJoin(loginCommand))
			.thenReturn(ALREADY_MEMBER_RESULT);

		// when
		LoginResult actual = kakaoAccountService.loginOrJoin(loginCommand);

		// then
		assertAll(
			() -> assertThat(actual).isNotNull().isEqualTo(ALREADY_MEMBER_RESULT),
			() -> verify(eventPublisher, never()).publish(any(InitCurrentCharacterEvent.class)),
			() -> verify(eventPublisher, never()).publish(any(InitEnergyPointEvent.class))
		);
	}
}
