package univ.earthbreaker.namu.core.domain.member.friend;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class FriendRegister {

	private final FriendRepository friendRepository;

	public FriendRegister(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	void register(@NotNull FriendRelationCommand command) {
		friendRepository.register(command.memberNo(), command.targetMemberNo());
	}
}
