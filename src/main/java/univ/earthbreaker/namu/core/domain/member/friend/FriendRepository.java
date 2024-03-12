package univ.earthbreaker.namu.core.domain.friend;

import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository {

	 void register(long memberNo, long targetMemberNo);
}
