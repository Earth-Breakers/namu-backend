package univ.earthbreaker.namu.core.domain.character.book;

public record CharacterProfileResult(
	long characterNo,
	String thumbnailImagePath,
	boolean isAcquired
) {
}
