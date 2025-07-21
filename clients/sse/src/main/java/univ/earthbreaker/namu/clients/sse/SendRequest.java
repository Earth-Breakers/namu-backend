package univ.earthbreaker.namu.clients.sse;

public record SendRequest(
	String eventName,
	Object data
) {
}
