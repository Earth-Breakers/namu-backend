package univ.earthbreaker.namu.core.domain.pushnotification;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface FriendBridge {
	@NotNull FriendsQuery findFriends(long memberNo);
}
