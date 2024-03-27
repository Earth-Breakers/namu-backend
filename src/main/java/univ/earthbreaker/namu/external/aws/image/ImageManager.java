package univ.earthbreaker.namu.external.aws.image;

import jakarta.validation.constraints.NotNull;

public interface ImageManager {

	@NotNull String upload(@NotNull ImageUploadCommand command);

	void delete(String imagePathKey);
}
