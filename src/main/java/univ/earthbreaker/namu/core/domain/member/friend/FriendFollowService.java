package univ.earthbreaker.namu.core.domain.member.friend;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class FriendFollowService {

	private final FriendMemberBridge friendMemberBridge;
	private final FriendRegister friendRegister;

	public FriendFollowService(FriendMemberBridge friendMemberBridge, FriendRegister friendRegister) {
		this.friendMemberBridge = friendMemberBridge;
		this.friendRegister = friendRegister;
	}

	public void follow(@NotNull FriendRelationCommand friendRelationCommand) {
		friendMemberBridge.checkExist(friendRelationCommand.targetMemberNo());
		friendRegister.register(friendRelationCommand);
	}
}
