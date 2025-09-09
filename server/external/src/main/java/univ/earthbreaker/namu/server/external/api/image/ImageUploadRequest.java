package univ.earthbreaker.namu.server.external.api.image;

import java.io.InputStream;

public record ImageUploadRequest(
	String bucketName,
	String key,
	InputStream inputStream,
	ObjectMetaData objectMetaData
) {
}
