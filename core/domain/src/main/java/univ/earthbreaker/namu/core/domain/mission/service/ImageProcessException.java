package univ.earthbreaker.namu.core.domain.mission.service;

import org.jetbrains.annotations.NotNull;

public class ImageProcessException extends RuntimeException {

	public ImageProcessException(String message) {
		super(message);
	}

	public static @NotNull ImageProcessException uploadFail(String message, String imagePath) {
		return new ImageProcessException(String.format("이미지 (%s) 업로드에 실패했습니다 : %s", imagePath, message));
	}
}
