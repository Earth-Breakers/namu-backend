package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.event.image.DeleteUploadedImageEvent;

@Component
public class AwsS3ImageEventHandler {

	private final ImageManager imageManager;

	public AwsS3ImageEventHandler(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	@EventListener
	public void deleteImage(@NotNull DeleteUploadedImageEvent event) {
		imageManager.delete(event.imagePathKey());
	}
}
