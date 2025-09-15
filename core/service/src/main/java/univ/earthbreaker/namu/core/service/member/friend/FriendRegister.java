package univ.earthbreaker.namu.core.service.member.friend;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.friend.FriendRelationCommand;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendRepository;

@Component
public class FriendRegister {

	private final FriendRepository friendRepository;

	public FriendRegister(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	public void register(FriendRelationCommand command) {
		if (!friendRepository.existsBy(command.memberNo(), command.targetMemberNo())) {
			friendRepository.register(command.memberNo(), command.targetMemberNo());
		}
	}
}
