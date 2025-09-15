package univ.earthbreaker.namu.infra.storage.character;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public interface CharacterProjection {
	long getNo();
	int getGroupNumber();
	int getLevel();
	CharacterType getType();
	int getRequiredExp();
	String getName();
	String getMainImagePath();
	String getBackgroundImagePath();
	String getScripts();
}
