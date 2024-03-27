package univ.earthbreaker.namu.core.domain.mission;

public enum MissionStatus {

	READY("도전"),
	IN_PROGRESS("도전 중"),
	SUCCESS("성공"),
	FAILURE("실패"),
	;

	private final String value;

	MissionStatus(String value) {
		this.value = value;
	}
}
