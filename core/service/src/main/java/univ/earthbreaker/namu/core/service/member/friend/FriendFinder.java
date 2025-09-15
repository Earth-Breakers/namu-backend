package univ.earthbreaker.namu.core.service.member.friend;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.member.friend.Friend;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendRepository;

@Component
public class FriendFinder {

	private final FriendRepository friendRepository;

	FriendFinder(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	Friend findAll(long memberNo) {
		return friendRepository.findAll(memberNo);
	}
}
