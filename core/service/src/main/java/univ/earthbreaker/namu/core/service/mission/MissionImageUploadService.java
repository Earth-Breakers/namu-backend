package univ.earthbreaker.namu.core.domain.mission.service;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.mission.infra.ImageManager;
import univ.earthbreaker.namu.core.domain.mission.infra.ImageUploadCommand;

@Component
public class MissionImageUploadService {

	private final ImageManager imageManager;

	public MissionImageUploadService(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public ImageUploadResult process(String requestId, Long memberNo, Long missionNo) {
		String imagePathKey = requestId + "-" + memberNo + "-" + missionNo;
		try {
			if (imageManager.retrieve(imagePathKey) == null) {
				imageManager.upload(new ImageUploadCommand(requestId, memberNo, missionNo, imagePathKey));
			}
			return new ImageUploadResult(imagePathKey, true);
		} catch (ImageProcessException e) {
			return new ImageUploadResult(imagePathKey, false);
		}
	}
}
