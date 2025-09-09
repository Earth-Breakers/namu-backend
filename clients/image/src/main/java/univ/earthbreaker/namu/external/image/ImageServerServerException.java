package univ.earthbreaker.namu.external.image;

class ImageServerServerException extends RuntimeException {

	public ImageServerServerException(String message) {
		super(message);
	}

	static ImageServerServerException uploadFail(String message, String key) {
		return new ImageServerServerException(message + ": " + key);
	}
}
