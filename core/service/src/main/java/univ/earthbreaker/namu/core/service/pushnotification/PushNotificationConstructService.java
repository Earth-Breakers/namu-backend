package univ.earthbreaker.namu.core.service.pushnotification;

import static univ.earthbreaker.namu.core.domain.pushnotification.infra.ShowOffNotificationPort.PushNotificationSourceCommand;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.pushnotification.PushNotificationConstructResult;
import univ.earthbreaker.namu.core.domain.pushnotification.infra.FriendsQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.infra.ShowOffNotificationPort;

@Service
public class PushNotificationConstructService {

	private final PushNotificationFinder pushNotificationFinder;
	private final FriendBridge friendBridge;
	private final MemberBridge memberBridge;
	private final CurrentCharacterBridge currentCharacterBridge;
	private final ShowOffNotificationPort notificationPort;

	public PushNotificationConstructService(
		PushNotificationFinder pushNotificationFinder,
		FriendBridge friendBridge,
		MemberBridge memberBridge,
		CurrentCharacterBridge currentCharacterBridge,
		ShowOffNotificationPort notificationPort
	) {
		this.pushNotificationFinder = pushNotificationFinder;
		this.friendBridge = friendBridge;
		this.memberBridge = memberBridge;
		this.currentCharacterBridge = currentCharacterBridge;
		this.notificationPort = notificationPort;
	}

	public void findAllMemberNotificationToken(long memberNo) {
		PushNotificationConstructResult result = PushNotificationConstructResult.of(
			memberBridge.findMember(memberNo),
			currentCharacterBridge.findCurrentCharacter(memberNo),
			pushNotificationFinder.findAllEnable()
		);
		notificationPort.sendShowOffMessage(
			new PushNotificationSourceCommand(
				result.nickname(),
				result.characterName(),
				null,
				result.notificationTokens())
		);
	}

	public void findFriendsNotificationToken(long memberNo, String content) {
		FriendsQuery friends = friendBridge.findFriends(memberNo);
		PushNotificationConstructResult result = PushNotificationConstructResult.of(
			memberBridge.findMember(memberNo),
			currentCharacterBridge.findCurrentCharacter(memberNo),
			pushNotificationFinder.findFriendsEnable(friends)
		);
		notificationPort.sendShowOffMessage(
			new PushNotificationSourceCommand(
				result.nickname(),
				result.characterName(),
				content,
				result.notificationTokens()
			));
	}
}
