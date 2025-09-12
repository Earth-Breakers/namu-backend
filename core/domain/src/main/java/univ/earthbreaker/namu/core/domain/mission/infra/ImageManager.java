package univ.earthbreaker.namu.core.domain.mission.infra;

import univ.earthbreaker.namu.core.domain.mission.service.ImageProcessException;

public interface ImageManager {

	void upload(ImageUploadCommand command) throws ImageProcessException;

	String retrieve(String imagePathKey) throws ImageProcessException;
}
