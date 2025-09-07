package univ.earthbreaker.namu.server.internal.api;

public record SseSendRequest(
	String eventName,
	Object data
) {
}
