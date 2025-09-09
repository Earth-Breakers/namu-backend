package univ.earthbreaker.namu.external.image;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.server.external.api.image.ImageRequest;
import univ.earthbreaker.namu.server.external.api.image.ImageUploadRequest;
import univ.earthbreaker.namu.server.external.api.image.ObjectMetaData;
import univ.earthbreaker.namu.server.external.exception.ExternalImageServerException;

@Component
public class ExternalImageManager implements ImageManager {

	private static final String BUCKET_NAME = "my-bucket";

	private final ImageApiCaller imageApiCaller;

	public ExternalImageManager(ImageApiCaller imageApiCaller) {
		this.imageApiCaller = imageApiCaller;
	}

	@Override
	public String upload(ImageUploadCommand command) {
		ObjectMetaData objectMetadata = new ObjectMetaData();
		objectMetadata.setContentType(command.contentType());
		objectMetadata.setContentLength(command.contentLength());

		String imagePathKey = command.imagePathKey();
		try {
			imageApiCaller.uploadImage(new ImageUploadRequest(BUCKET_NAME, imagePathKey, command.inputStream(), objectMetadata));
		} catch (ExternalImageServerException e) {
			throw ImageServerServerException.uploadFail(e.getMessage(), imagePathKey);
		}
		return imagePathKey;
	}

	@Override
	public String retrieve(String imagePathKey) {
		ImageRequest request = new ImageRequest(BUCKET_NAME, imagePathKey);
		ExternalImageResult response = imageApiCaller.getImage(request);
		return response.data().toString();
	}

	@Override
	public void delete(String imagePathKey) {
		ImageRequest request = new ImageRequest(BUCKET_NAME, imagePathKey);
		imageApiCaller.deleteImage(request);
	}
}
