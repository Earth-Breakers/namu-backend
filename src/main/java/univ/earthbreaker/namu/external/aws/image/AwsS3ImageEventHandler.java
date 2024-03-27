package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.event.image.DeleteUploadedImageEvent;

@Component
public class AwsS3ImageEventHandler {

	private final ImageManager imageManager;

	public AwsS3ImageEventHandler(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void deleteImage(@NotNull DeleteUploadedImageEvent event) {
		imageManager.delete(event.imagePathKey());
	}
}
