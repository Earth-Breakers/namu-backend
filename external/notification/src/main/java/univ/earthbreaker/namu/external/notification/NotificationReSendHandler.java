package univ.earthbreaker.namu.external.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationReSendHandler {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationReSendHandler.class);
	private static final String MESSAGE_SEND_FINISH_FORMAT = "메세지 전송을 완료했습니다 성공 : {} 실패 : {}";
	private static final String MESSAGE_SEND_FAILURE_FORMAT = "전송에 실패한 메시지 : {}";
	private static final String MESSAGE_RETRY_COUNT_FORMAT = "메시지 전송 재시도 횟수 : {}";

	private final FirebaseBackOff firebaseBackOff;
	private final NotificationReSender notificationReSender;

	public NotificationReSendHandler(FirebaseBackOff firebaseBackOff, NotificationReSender notificationReSender) {
		this.firebaseBackOff = firebaseBackOff;
		this.notificationReSender = notificationReSender;
	}

	void handleFailureMessage(@NotNull PushNotificationSourceCommand failureSourceCommand) {
		List<String> failureNotificationTokens = new ArrayList<>(failureSourceCommand.notificationTokens());
		BatchMessageResponse messageResponse = null;
		messageResponse = retryForFailedNotifications(failureSourceCommand, failureNotificationTokens, messageResponse);
		logNotificationFinalResponse(messageResponse, firebaseBackOff.getCurrentRetryCount(), failureNotificationTokens);
		firebaseBackOff.reset();
	}

	private @NotNull BatchMessageResponse retryForFailedNotifications(
		@NotNull PushNotificationSourceCommand failureSourceCommand,
		@NotNull List<String> failureNotificationTokens,
		@Nullable BatchMessageResponse messageResponse
	) {
		while (!firebaseBackOff.isStopped() && !failureNotificationTokens.isEmpty()) {
			firebaseBackOff.nextBackOffMillis();
			firebaseBackOff.waitUntilInterval();
			messageResponse = notificationReSender.resendFailure(failureSourceCommand);
			failureNotificationTokens = getFailureNotificationTokens(failureNotificationTokens, messageResponse);
		}
		assert messageResponse != null;
		return messageResponse;
	}

	private List<String> getFailureNotificationTokens(
		List<String> failureNotificationTokens,
		@NotNull BatchMessageResponse messageResponse
	) {
		if (messageResponse.hasFailure()) {
			return messageResponse.extractFailureTokens(failureNotificationTokens);
		} else {
			return Collections.emptyList();
		}
	}

	private void logNotificationFinalResponse(
		@NotNull BatchMessageResponse messageResponse,
		long finalRetryCount,
		List<String> failureNotificationTokens
	) {
		LOG.info(MESSAGE_SEND_FAILURE_FORMAT, messageResponse.getFailureTokensAndReasons(failureNotificationTokens));
		LOG.info(MESSAGE_RETRY_COUNT_FORMAT, finalRetryCount);
		LOG.info(MESSAGE_SEND_FINISH_FORMAT, messageResponse.getSuccessCount(), messageResponse.getFailureCount());
	}
}
