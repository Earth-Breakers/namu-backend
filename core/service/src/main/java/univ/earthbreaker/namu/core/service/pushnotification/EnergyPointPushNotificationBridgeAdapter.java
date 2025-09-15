package univ.earthbreaker.namu.core.service.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.service.point.EnergyPointPushNotificationBridge;
import univ.earthbreaker.namu.core.domain.pushnotification.MemberQuery;
import univ.earthbreaker.namu.core.domain.pushnotification.PushNotification;

@Component
public class EnergyPointPushNotificationBridgeAdapter implements EnergyPointPushNotificationBridge {

	private final PushNotificationFinder pushNotificationFinder;
	private final MemberBridge memberBridge;

	public EnergyPointPushNotificationBridgeAdapter(
		PushNotificationFinder pushNotificationFinder,
		MemberBridge memberBridge
	) {
		this.pushNotificationFinder = pushNotificationFinder;
		this.memberBridge = memberBridge;
	}

	@Override
	public GiftResult find(long memberNo, long targetMemberNo) {
		PushNotification targetPushNotification = pushNotificationFinder.find(targetMemberNo);
		MemberQuery memberQuery = memberBridge.findMember(memberNo);
		if (targetPushNotification.isEnable()) {
			return new GiftResult(memberQuery.nickname(), targetPushNotification.getToken());
		}
		return new GiftResult(memberQuery.nickname(), null);
	}
}
