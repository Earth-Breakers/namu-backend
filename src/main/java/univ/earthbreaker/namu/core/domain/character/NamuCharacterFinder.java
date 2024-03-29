package univ.earthbreaker.namu.core.domain.character;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NamuCharacterFinder {

	private final CharacterRepository characterRepository;

	public NamuCharacterFinder(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public @NotNull NamuCharacter findNext(int level, int groupNumber, CharacterType type) {
		NextDeterminedDbQuery nextDeterminedDbQuery = new NextDeterminedDbQuery(level, groupNumber, type);
		NamuCharacter namuCharacter = characterRepository.findOrNull(nextDeterminedDbQuery);
		if (namuCharacter != null) {
			return namuCharacter;
		}
		throw NamuCharacterNotFoundException.notFoundNext();
	}

	public @NotNull NamuCharacter findRandom(int level, int groupNumber, boolean isEndangered, CharacterType type) {
		NextRandomCharacterDbQuery nextRandomCharacterDbQuery
			= new NextRandomCharacterDbQuery(level, groupNumber, isEndangered, type);
		NamuCharacter namuCharacter = characterRepository.findRandomOrNull(nextRandomCharacterDbQuery);
		if (namuCharacter != null) {
			return namuCharacter;
		}
		throw NamuCharacterNotFoundException.notFoundRandom();
	}
}
