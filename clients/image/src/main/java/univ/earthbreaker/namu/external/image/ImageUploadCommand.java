package univ.earthbreaker.namu.external.image;

import java.io.InputStream;

public record ImageUploadCommand(
	String imagePathKey,
	String contentType,
	long contentLength,
	InputStream inputStream
) {
}
