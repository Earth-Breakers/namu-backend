package univ.earthbreaker.namu.app.api.mission;

public class MissionServerException extends RuntimeException {

	public MissionServerException(String message) {
		super(message);
	}

	static MissionServerException missingImageUrl() {
		return new MissionServerException("Image upload returned null");
	}
}
