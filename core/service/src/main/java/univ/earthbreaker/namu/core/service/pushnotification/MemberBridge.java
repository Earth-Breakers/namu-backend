package univ.earthbreaker.namu.core.service.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.MemberQuery;

@Component
public interface MemberBridge {
	MemberQuery findMember(long memberNo);
}
