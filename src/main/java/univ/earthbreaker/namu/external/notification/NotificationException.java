package univ.earthbreaker.namu.external.notification;

import org.jetbrains.annotations.NotNull;

public class NotificationException extends RuntimeException {

	private NotificationException(String message) {
		super(message);
	}

	static @NotNull NotificationException sendAsync(String message) {
		return new NotificationException(String.format("비동기 메시지 전송 중 예외 발생 (%s)", message));
	}

	static @NotNull NotificationException threadInterrupt(String message) {
		return new NotificationException(String.format("BackOff 수행 중 thread sleep interrupt 예외 발생 (%s)", message));

	}
}
