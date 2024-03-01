package univ.earthbreaker.namu.core.domain.character;

public enum CharacterType {

	BEAUTY("아름다움"),
	VITALITY("생명력"),
	PURIFY("정화"),
	INITIAL("묘목");

	private final String value;

	CharacterType(String value) {
		this.value = value;
	}

	boolean isInitial() {
		return this == INITIAL;
	}
}
