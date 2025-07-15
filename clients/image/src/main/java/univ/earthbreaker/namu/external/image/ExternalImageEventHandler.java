package univ.earthbreaker.namu.external.image;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.event.image.DeleteExternalUploadedImageEvent;

@Component
class ExternalImageEventHandler {

	private final ExternalImageManager imageManager;

	public ExternalImageEventHandler(ExternalImageManager imageManager) {
		this.imageManager = imageManager;
	}

	@EventListener
	public void deleteImage(@NotNull DeleteExternalUploadedImageEvent event) {
		imageManager.delete(event.imageKey());
	}
}
