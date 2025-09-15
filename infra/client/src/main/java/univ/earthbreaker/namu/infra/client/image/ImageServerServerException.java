package univ.earthbreaker.namu.infra.client.image;

public class ImageServerServerException extends RuntimeException {

	public ImageServerServerException(String message) {
		super(message);
	}

	static ImageServerServerException uploadFail(String message, String key) {
		return new ImageServerServerException(message + ": " + key);
	}
}
