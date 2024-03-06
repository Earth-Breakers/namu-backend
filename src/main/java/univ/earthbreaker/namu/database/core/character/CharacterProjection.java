package univ.earthbreaker.namu.database.core.character;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public interface CharacterProjection {
	long getNo();
	int getGroupNumber();
	int getLevel();
	CharacterType getType();
	int getRequiredExp();
	String getMainImagePath();
}
