package univ.earthbreaker.namu.core.domain.friend;

import org.springframework.stereotype.Service;

@Service
public class FriendFollowService {

	private final FriendRegister friendRegister;

	public FriendFollowService(FriendRegister friendRegister) {
		this.friendRegister = friendRegister;
	}

	public void register(FriendRelationCommand friendRelationCommand) {
		friendRegister.register(friendRelationCommand);
	}
}
