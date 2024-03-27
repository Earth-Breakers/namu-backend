package univ.earthbreaker.namu.core.domain.pushnotification;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.NAME;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER_NICKNAME;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_MEMBER_NO_1;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_MEMBER_NO_2;
import static univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationFixture.TARGET_PUSH_NOTIFICATION_ENABLE;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PushNotificationConstructServiceTest {

	private @Mock PushNotificationFinder pushNotificationFinder;
	private @Mock FriendBridge friendBridge;
	private @Mock MemberBridge memberBridge;
	private @Mock CurrentCharacterBridge currentCharacterBridge;
	private @InjectMocks PushNotificationConstructService pushNotificationConstructService;

	@DisplayName("알림을 전송할 모든 회원의 푸시 알림 토큰값, 회원의 정보, 자랑할 현재 캐릭터 정보를 가져와 푸시 알림에 사용될 정보를 구성한다")
	@Test
	void findAllMemberNotificationToken() {
		// given
		when(memberBridge.findMember(MEMBER_NO))
			.thenReturn(new MemberQuery(MEMBER_NICKNAME));
		when(currentCharacterBridge.findCurrentCharacter(MEMBER_NO))
			.thenReturn(new CharacterQuery(NAME));
		when(pushNotificationFinder.findAllEnable())
			.thenReturn(List.of(TARGET_PUSH_NOTIFICATION_ENABLE));

		// when
		PushNotificationConstructResult result = pushNotificationConstructService.findAllMemberNotificationToken(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(result).isNotNull(),
			() -> assertThat(result.nickname()).isEqualTo(MEMBER_NICKNAME),
			() -> assertThat(result.characterName()).isEqualTo(NAME),
			() -> assertThat(result.notificationTokens()).isNotNull(),
			() -> assertThat(result.notificationTokens()).asList().hasSize(1),
			() -> assertThat(result.notificationTokens()).asList().contains(TARGET_PUSH_NOTIFICATION_ENABLE.getToken())
		);
	}

	@DisplayName("알림을 전송할 친구들의 푸시 알림 토큰값, 회원의 정보, 자랑할 현재 캐릭터 정보를 가져와 푸시 알림에 사용될 정보를 구성한다")
	@Test
	void findFriendsNotificationToken() {
		// given
		FriendsQuery friends = new FriendsQuery(List.of(TARGET_MEMBER_NO_1, TARGET_MEMBER_NO_2));
		when(friendBridge.findFriends(MEMBER_NO))
			.thenReturn(friends);
		when(memberBridge.findMember(MEMBER_NO))
			.thenReturn(new MemberQuery(MEMBER_NICKNAME));
		when(currentCharacterBridge.findCurrentCharacter(MEMBER_NO))
			.thenReturn(new CharacterQuery(NAME));
		when(pushNotificationFinder.findFriendsEnable(friends))
			.thenReturn(List.of(TARGET_PUSH_NOTIFICATION_ENABLE));

		// when
		PushNotificationConstructResult result = pushNotificationConstructService.findFriendsNotificationToken(MEMBER_NO);

		// then
		assertAll(
			() -> assertThat(result).isNotNull(),
			() -> assertThat(result.nickname()).isEqualTo(MEMBER_NICKNAME),
			() -> assertThat(result.characterName()).isEqualTo(NAME),
			() -> assertThat(result.notificationTokens()).isNotNull(),
			() -> assertThat(result.notificationTokens()).asList().hasSize(1),
			() -> assertThat(result.notificationTokens()).asList().contains(TARGET_PUSH_NOTIFICATION_ENABLE.getToken())
		);
	}
}
