package univ.earthbreaker.namu.core.domain.mission.infra;

public interface ImageManager {

	void upload(ImageUploadCommand command) throws ImageProcessException;

	String retrieve(String imagePathKey) throws ImageProcessException;
}
