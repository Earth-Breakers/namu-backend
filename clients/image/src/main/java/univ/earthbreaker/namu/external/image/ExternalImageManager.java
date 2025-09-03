package univ.earthbreaker.namu.external.image;

import org.springframework.stereotype.Component;

@Component
public class ExternalImageManager implements ImageManager {

	private final ImageApiCaller imageApiCaller;

	public ExternalImageManager(ImageApiCaller imageApiCaller) {
		this.imageApiCaller = imageApiCaller;
	}

	@Override
	public String upload(ImageUploadCommand command) {
		ExternalImageResult response = imageApiCaller.uploadImage();
		return response.message();
	}

	@Override
	public String retrieve(String imagePathKey) {
		ExternalImageResult response = imageApiCaller.getImage();
		return response.data().toString();
	}

	@Override
	public void delete(String imagePathKey) {
		imageApiCaller.deleteImage();
	}
}
