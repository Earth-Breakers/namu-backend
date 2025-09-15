package univ.earthbreaker.namu.core.service.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.MemberQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotification;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FollowFriendPushNotificationBridge;

@Component
public class FollowFriendPushNotificationBridgeAdapter implements FollowFriendPushNotificationBridge {

	private final PushNotificationFinder pushNotificationFinder;
	private final MemberBridge memberBridge;

	public FollowFriendPushNotificationBridgeAdapter(
		PushNotificationFinder pushNotificationFinder,
		MemberBridge memberBridge
	) {
		this.pushNotificationFinder = pushNotificationFinder;
		this.memberBridge = memberBridge;
	}

	@Override
	public FollowResult find(long memberNo, long targetMemberNo) {
		PushNotification targetPushNotification = pushNotificationFinder.find(targetMemberNo);
		MemberQuery memberQuery = memberBridge.findMember(memberNo);
		MemberQuery targetMemberQuery = memberBridge.findMember(targetMemberNo);
		if (!targetPushNotification.isEnable()) {
			return new FollowResult(memberQuery.nickname(), targetMemberQuery.nickname(), null);
		}
		return new FollowResult(memberQuery.nickname(), targetMemberQuery.nickname(), targetPushNotification.getToken());
	}
}
