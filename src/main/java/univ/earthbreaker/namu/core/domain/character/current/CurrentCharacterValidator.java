package univ.earthbreaker.namu.core.domain.character.current;

import org.jetbrains.annotations.NotNull;

public class CurrentCharacterValidator {

	private CurrentCharacterValidator() {
	}

	static void validateCanLevelUp(@NotNull CurrentCharacter currentCharacter) {
		if (currentCharacter.cannotLevelUp()) {
			throw new IllegalStateException("현재 캐릭터는 아직 성장할 수 없는 상태입니다");
		}
	}

	static void validateLevelIsBegin(@NotNull CurrentCharacter currentCharacter) {
		if (currentCharacter.levelIsNotBegin()) {
			throw new IllegalStateException("레벨이 BEGIN 인 캐릭터만 growToRandom 메서드를 호출할 수 있습니다");
		}
	}

	static void validateLevelIsMiddle(@NotNull CurrentCharacter currentCharacter) {
		if (currentCharacter.levelIsNotMiddle()) {
			throw new IllegalStateException("레벨이 MIDDLE 인 캐릭터만 growToNext 메서드를 호출할 수 있습니다");
		}
	}
}
