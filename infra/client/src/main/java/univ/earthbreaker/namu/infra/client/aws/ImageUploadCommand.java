package univ.earthbreaker.namu.infra.client.aws;

import java.io.InputStream;

import org.jetbrains.annotations.NotNull;

public record ImageUploadCommand(
	String memberKey,
	String contentType,
	long contentLength,
	String originalFilename,
	InputStream inputStream,
	ImagePathKeyGenerator imagePathKeyGenerator
) {
	public static @NotNull ImageUploadCommand forMember(
		long memberNo,
		String contentType,
		long contentLength,
		String originalFilename,
		InputStream inputStream,
		ImagePathKeyGenerator imagePathKeyGenerator
	) {
		return new ImageUploadCommand(
			String.valueOf(memberNo),
			contentType,
			contentLength,
			originalFilename,
			inputStream,
			imagePathKeyGenerator
		);
	}
}
