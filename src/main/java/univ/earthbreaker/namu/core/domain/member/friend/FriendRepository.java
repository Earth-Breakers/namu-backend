package univ.earthbreaker.namu.core.domain.member.friend;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository {

	 void register(long memberNo, long targetMemberNo);

	 @NotNull Friend find(long memberNo);
}
