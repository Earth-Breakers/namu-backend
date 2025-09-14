package univ.earthbreaker.namu.infra.notification;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendNotificationPort;

@Component
public class FriendNotificationAdapter implements FriendNotificationPort {

	private final FirebaseMessaging firebaseMessaging;

	public FriendNotificationAdapter(FirebaseMessaging firebaseMessaging) {
		this.firebaseMessaging = firebaseMessaging;
	}

	@Override
	public void sendAfterFollow(FollowPushNotificationSourceCommand sourceCommand) {
		String title = String.format("%s 님이 %s 님을 팔로우해요", sourceCommand.nickname(), sourceCommand.targetNickname());
		Message message = MessageCreator.create(sourceCommand.notificationToken(), title, null);
		firebaseMessaging.sendAsync(message);
	}
}
