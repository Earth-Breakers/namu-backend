package univ.earthbreaker.namu.core.domain.member.friend;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendRepository;

@Component
public class FriendRegister {

	private final FriendRepository friendRepository;

	public FriendRegister(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	void register(@NotNull FriendRelationCommand command) {
		if (!friendRepository.existsBy(command.memberNo(), command.targetMemberNo())) {
			friendRepository.register(command.memberNo(), command.targetMemberNo());
		}
	}
}
