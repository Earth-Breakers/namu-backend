package univ.earthbreaker.namu.core.domain.pushnotification;

import org.springframework.stereotype.Service;

@Service
public class PushNotificationConstructService {

	private final PushNotificationFinder pushNotificationFinder;
	private final FriendBridge friendBridge;
	private final MemberBridge memberBridge;
	private final CurrentCharacterBridge currentCharacterBridge;

	public PushNotificationConstructService(
		PushNotificationFinder pushNotificationFinder,
		FriendBridge friendBridge,
		MemberBridge memberBridge,
		CurrentCharacterBridge currentCharacterBridge
	) {
		this.pushNotificationFinder = pushNotificationFinder;
		this.friendBridge = friendBridge;
		this.memberBridge = memberBridge;
		this.currentCharacterBridge = currentCharacterBridge;
	}

	public PushNotificationConstructResult findAllMemberNotificationToken(long memberNo) {
		return PushNotificationConstructResult.of(
			memberBridge.findMember(memberNo),
			currentCharacterBridge.findCurrentCharacter(memberNo),
			pushNotificationFinder.findAllEnable()
		);
	}

	public PushNotificationConstructResult findFriendsNotificationToken(long memberNo) {
		FriendsQuery friends = friendBridge.findFriends(memberNo);
		return PushNotificationConstructResult.of(
			memberBridge.findMember(memberNo),
			currentCharacterBridge.findCurrentCharacter(memberNo),
			pushNotificationFinder.findFriendsEnable(friends)
		);
	}
}
