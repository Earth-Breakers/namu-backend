package univ.earthbreaker.namu.external.image;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.external.aws.image.ImageManager;
import univ.earthbreaker.namu.external.aws.image.ImageUploadCommand;
import univ.earthbreaker.namu.external.server.ExternalImageResponse;

@Component
public class ExternalImageManager implements ImageManager {

	private final ImageApiCaller imageApiCaller;

	public ExternalImageManager(ImageApiCaller imageApiCaller) {
		this.imageApiCaller = imageApiCaller;
	}

	@Override
	public String upload(ImageUploadCommand command) {
		ExternalImageResponse response = imageApiCaller.uploadImage();
		return response.message();
	}

	@Override
	public void delete(String imagePathKey) {
		imageApiCaller.deleteImage();
	}
}
