package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NamuCharacterFinder {

	private final CharacterRepository characterRepository;

	public NamuCharacterFinder(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public @NotNull NamuCharacter findNext(int level, int groupNumber, CharacterType characterType) {
		NextDeterminedRequestDto requestDto = new NextDeterminedRequestDto(level, groupNumber, characterType);
		NamuCharacter namuCharacter = characterRepository.findOrNull(requestDto);
		if (namuCharacter != null) {
			return namuCharacter;
		}
		throw NamuCharacterNotFoundException.notFoundNext();
	}

	public @NotNull NamuCharacter findRandom(int level, int groupNumber, boolean isEndangered, CharacterType characterType) {
		NextRandomCharacterRequestDto requestDto =
			new NextRandomCharacterRequestDto(level, groupNumber, isEndangered, characterType);
		NamuCharacter namuCharacter = characterRepository.findRandomOrNull(requestDto);
		if (namuCharacter != null) {
			return namuCharacter;
		}
		throw NamuCharacterNotFoundException.notFoundRandom();
	}
}
