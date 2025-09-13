package univ.earthbreaker.namu.core.domain.member.friend;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendRepository;

@Component
class FriendFinder {

	private final FriendRepository friendRepository;

	FriendFinder(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	Friend findAll(long memberNo) {
		return friendRepository.findAll(memberNo);
	}
}
