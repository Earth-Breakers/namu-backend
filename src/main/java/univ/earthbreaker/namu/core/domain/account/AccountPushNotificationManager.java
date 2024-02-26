package univ.earthbreaker.namu.core.domain.account;

import org.springframework.stereotype.Component;

@Component
public interface AccountPushNotificationManager {

	void register(Long memberNo, String pushNotificationToken);

	void updateIfTokenModified(Long memberNo, String pushNotificationToken);
}
