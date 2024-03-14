package univ.earthbreaker.namu.core.domain.member.friend;

import org.springframework.stereotype.Service;

@Service
public class FriendFinderService {

	private final FriendFinder friendFinder;

	public FriendFinderService(FriendFinder friendFinder) {
		this.friendFinder = friendFinder;
	}

	public Friend findMyFriendList(long memberNo) {
		return friendFinder.find(memberNo);
	}
}
