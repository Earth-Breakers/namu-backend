package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ImageUploadCommand(
	String memberKey,
	MultipartFile imageFile,
	ImagePathKeyGenerator imagePathKeyGenerator
) {
	public static @NotNull ImageUploadCommand forMember(
		long memberNo,
		MultipartFile imageFile,
		ImagePathKeyGenerator imagePathKeyGenerator
	) {
		return new ImageUploadCommand(String.valueOf(memberNo), imageFile, imagePathKeyGenerator);
	}
}
