package univ.earthbreaker.namu.server.external.api.image;

public record ImageRequest(
	String bucketName,
	String key
) {
}
