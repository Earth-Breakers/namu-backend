package univ.earthbreaker.namu.infra.client.image;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.infra.ImageManager;
import univ.earthbreaker.namu.core.domain.mission.infra.ImageUploadCommand;
import univ.earthbreaker.namu.core.domain.mission.infra.ImageProcessException;
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
	public void upload(ImageUploadCommand command) throws ImageProcessException {
		ObjectMetaData objectMetadata = new ObjectMetaData();
		objectMetadata.setContentType("command.contentType()");
		objectMetadata.setContentLength(0); // command.contentLength()

		String imagePathKey = command.imagePathKey();
		try {
			imageApiCaller.uploadImage(new ImageUploadRequest(BUCKET_NAME, imagePathKey, /*command.inputStream()*/ InputStream.nullInputStream(), objectMetadata));
		} catch (ExternalImageServerException e) {
			throw ImageProcessException.uploadFail(e.getMessage(), imagePathKey);
		}
	}

	@Override
	public String retrieve(String imagePathKey) {
		ImageRequest request = new ImageRequest(BUCKET_NAME, imagePathKey);
		ExternalImageResult response = imageApiCaller.getImage(request);
		return response.data().toString();
	}
}
