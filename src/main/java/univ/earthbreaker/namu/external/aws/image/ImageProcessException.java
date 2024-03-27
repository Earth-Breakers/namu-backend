package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;

public class ImageProcessException extends RuntimeException {

	private ImageProcessException(String message) {
		super(message);
	}

	public static @NotNull ImageProcessException uploadFail(String message, String imagePath) {
		return new ImageProcessException(String.format("AWS S3 이미지 (%s) 업로드에 실패했습니다 : %s", imagePath, message));
	}

	public static @NotNull ImageProcessException deleteFail(String message, String imagePath) {
		return new ImageProcessException(String.format("AWS S3 이미지 (%s) 삭제에 실패했습니다 : %s", imagePath, message));
	}
}
