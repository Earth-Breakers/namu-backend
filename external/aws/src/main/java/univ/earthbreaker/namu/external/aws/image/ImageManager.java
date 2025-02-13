package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;

public interface ImageManager {

	@NotNull String upload(@NotNull ImageUploadCommand command);

	void delete(String imagePathKey);
}
