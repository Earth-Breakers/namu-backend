package univ.earthbreaker.namu.external.notification;

import org.springframework.stereotype.Component;

@Component
public interface NotificationPort {

	void sendShowOffMessage(PushNotificationSourceCommand sourceCommand);
}
