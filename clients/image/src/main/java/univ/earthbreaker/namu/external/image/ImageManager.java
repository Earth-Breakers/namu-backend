package univ.earthbreaker.namu.external.image;

public interface ImageManager {

	String upload(ImageUploadCommand command);

	String retrieve(String imagePathKey);

	void delete(String imagePathKey);
}
