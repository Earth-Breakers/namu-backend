package univ.earthbreaker.namu.core.service.member.friend;

import static univ.earthbreaker.namu.core.domain.member.friend.infra.FollowFriendPushNotificationBridge.FollowResult;
import static univ.earthbreaker.namu.core.domain.member.friend.infra.FriendNotificationPort.FollowPushNotificationSourceCommand;

import org.springframework.stereotype.Service;

import univ.earthbreaker.namu.core.domain.member.friend.FriendRelationCommand;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FollowFriendPushNotificationBridge;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendMemberBridge;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendNotificationPort;

@Service
public class FriendFollowService {

	private final FriendMemberBridge friendMemberBridge;
	private final FriendRegister friendRegister;
	private final FollowFriendPushNotificationBridge followFriendPushNotificationBridge;
	private final FriendNotificationPort notificationPort;

	public FriendFollowService(
		FriendMemberBridge friendMemberBridge,
		FriendRegister friendRegister,
		FollowFriendPushNotificationBridge followFriendPushNotificationBridge,
		FriendNotificationPort notificationPort
	) {
		this.friendMemberBridge = friendMemberBridge;
		this.friendRegister = friendRegister;
		this.followFriendPushNotificationBridge = followFriendPushNotificationBridge;
		this.notificationPort = notificationPort;
	}

	public void follow(FriendRelationCommand command) {
		friendMemberBridge.checkExist(command.targetMemberNo());
		friendRegister.register(command);
		FollowResult result = followFriendPushNotificationBridge.find(command.memberNo(), command.targetMemberNo());
		notificationPort.sendAfterFollow(new FollowPushNotificationSourceCommand(
			result.memberNickname(),
			result.targetNickname(),
			result.targetTokenValue()
		));
	}
}
