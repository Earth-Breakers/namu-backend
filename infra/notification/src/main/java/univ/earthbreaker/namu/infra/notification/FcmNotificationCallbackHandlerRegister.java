package univ.earthbreaker.namu.infra.notification;

import static univ.earthbreaker.namu.core.domain.pushnotification.infra.ShowOffNotificationPort.PushNotificationSourceCommand;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.BatchResponse;


@Component
public class FcmNotificationCallbackHandlerRegister {

	private final NotificationReSendHandler notificationReSendHandler;

	public FcmNotificationCallbackHandlerRegister(NotificationReSendHandler notificationReSendHandler) {
		this.notificationReSendHandler = notificationReSendHandler;
	}

	void register(
		ApiFuture<BatchResponse> messageFuture,
		PushNotificationSourceCommand sourceCommand
	) {
		Runnable callbackHandler = () -> {
			BatchMessageResponse messageResponse = BatchMessageResponse.from(messageFuture);
			if (messageResponse.hasFailure()) {
				List<String> failureTokens = messageResponse.extractFailureTokens(sourceCommand.notificationTokens());
				notificationReSendHandler.handleFailureMessage(sourceCommand.changeToFailureTokens(failureTokens));
			}
		};
		messageFuture.addListener(callbackHandler, MoreExecutors.directExecutor());
	}
}
