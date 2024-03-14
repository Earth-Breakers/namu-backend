package univ.earthbreaker.namu.core.domain.member.friend;

import org.springframework.stereotype.Component;

@Component
class FriendFinder {

	private final FriendRepository friendRepository;

	FriendFinder(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	Friend find(long memberNo) {
		return friendRepository.find(memberNo);
	}
}
