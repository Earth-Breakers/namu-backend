package univ.earthbreaker.namu.external.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.jetbrains.annotations.NotNull;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.SendResponse;

public class BatchMessageResponse {

	private static final int ZERO = 0;

	private final BatchResponse response;

	private BatchMessageResponse(BatchResponse response) {
		this.response = response;
	}

	static @NotNull BatchMessageResponse from(@NotNull ApiFuture<BatchResponse> messageFuture) {
		try {
			return new BatchMessageResponse(messageFuture.get());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw NotificationException.sendAsync(e.getMessage());
		} catch (ExecutionException e) {
			throw NotificationException.sendAsync(e.getCause().getMessage());
		}
	}

	boolean hasFailure() {
		return response.getFailureCount() > ZERO;
	}

	Map<String, String> getFailureTokensAndReasons(List<String> fcmTokenValues) {
		List<SendResponse> sendResponses = response.getResponses();
		Map<String, String> failureTokenAndReasons = new HashMap<>();
		for (int i = 0; i < sendResponses.size(); i++) {
			SendResponse sendResponse = sendResponses.get(i);
			if (!sendResponse.isSuccessful()) {
				failureTokenAndReasons.put(
					fcmTokenValues.get(i).substring(ZERO, 3),
					sendResponse.getException().getMessage());
			}
		}
		return failureTokenAndReasons;
	}

	List<String> extractFailureTokens(List<String> fcmTokenValues) {
		List<SendResponse> sendResponses = response.getResponses();
		List<String> failureTokens = new ArrayList<>();
		for (int i = 0; i < sendResponses.size(); i++) {
			SendResponse sendResponse = sendResponses.get(i);
			if (!sendResponse.isSuccessful()) {
				failureTokens.add(fcmTokenValues.get(i));
			}
		}
		return failureTokens;
	}

	int getFailureCount() {
		return response.getFailureCount();
	}

	int getSuccessCount() {
		return response.getSuccessCount();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BatchMessageResponse that = (BatchMessageResponse)o;
		return response.equals(that.response);
	}

	@Override
	public int hashCode() {
		return Objects.hash(response);
	}
}
