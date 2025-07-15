package univ.earthbreaker.namu.core.domain.pushnotification;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface MemberBridge {
	@NotNull MemberQuery findMember(long memberNo);
}
