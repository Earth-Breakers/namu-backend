package univ.earthbreaker.namu.infra.client.aws;

import org.jetbrains.annotations.NotNull;

public interface ImageManager {

	@NotNull String upload(@NotNull ImageUploadCommand command);

	void delete(String imagePathKey);
}
