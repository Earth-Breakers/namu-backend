package univ.earthbreaker.namu.core.service.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.infra.FriendsQuery;

@Component
public interface FriendBridge {
	FriendsQuery findFriends(long memberNo);
}
