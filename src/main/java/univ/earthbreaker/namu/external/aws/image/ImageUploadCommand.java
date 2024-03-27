package univ.earthbreaker.namu.external.aws.image;

import org.springframework.web.multipart.MultipartFile;

public record ImageUploadCommand(
	Long memberNo,
	MultipartFile imageFile
) {
}
