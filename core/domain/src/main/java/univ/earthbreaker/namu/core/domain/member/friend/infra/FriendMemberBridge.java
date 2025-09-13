package univ.earthbreaker.namu.core.service.member.friend;

import org.springframework.stereotype.Component;

@Component
public interface FriendMemberBridge {
	void checkExist(long targetMemberNo);
}
