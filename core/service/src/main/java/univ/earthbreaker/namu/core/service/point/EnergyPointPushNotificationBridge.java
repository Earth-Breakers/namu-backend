package univ.earthbreaker.namu.core.service.point;

import org.springframework.stereotype.Component;

@Component
public interface EnergyPointPushNotificationBridge {

	GiftResult find(long memberNo, long targetMemberNo);

	record GiftResult(
		String memberNickname,
		String targetTokenValue
	) {
	}
}
