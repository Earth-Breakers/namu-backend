package univ.earthbreaker.namu.external.image;

public interface ImageManager {

	String upload(ImageUploadCommand command);

	void delete(String imagePathKey);
}
