package univ.earthbreaker.namu.external.image;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
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
		ObjectMapper objectMapper = new ObjectMapper();
		Response response = imageApiCaller.uploadImage();
		String result;
		try {
			ExternalImageResponse body = objectMapper.readValue(response.body().asInputStream(), ExternalImageResponse.class);
			result = body.message();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public void delete(String imagePathKey) {
		Response response = imageApiCaller.deleteImage();
	}
}
