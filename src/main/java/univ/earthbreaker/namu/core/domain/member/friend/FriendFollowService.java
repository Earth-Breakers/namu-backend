package univ.earthbreaker.namu.core.domain.member.friend;

import static univ.earthbreaker.namu.core.domain.member.friend.FollowFriendPushNotificationBridge.FollowResult;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class FriendFollowService {

	private final FriendMemberBridge friendMemberBridge;
	private final FriendRegister friendRegister;
	private final FollowFriendPushNotificationBridge followFriendPushNotificationBridge;

	public FriendFollowService(
		FriendMemberBridge friendMemberBridge,
		FriendRegister friendRegister,
		FollowFriendPushNotificationBridge followFriendPushNotificationBridge
	) {
		this.friendMemberBridge = friendMemberBridge;
		this.friendRegister = friendRegister;
		this.followFriendPushNotificationBridge = followFriendPushNotificationBridge;
	}

	public FollowResult follow(@NotNull FriendRelationCommand command) {
		friendMemberBridge.checkExist(command.targetMemberNo());
		friendRegister.register(command);
		return followFriendPushNotificationBridge.find(command.memberNo(), command.targetMemberNo());
	}
}
