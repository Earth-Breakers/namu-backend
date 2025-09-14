package univ.earthbreaker.namu.core.service.character;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.NamuCharacter;
import univ.earthbreaker.namu.core.domain.character.NamuCharacterNotFoundException;
import univ.earthbreaker.namu.core.domain.character.infra.CharacterRepository;
import univ.earthbreaker.namu.core.domain.character.infra.NextDeterminedDbQuery;
import univ.earthbreaker.namu.core.domain.character.infra.NextRandomCharacterDbQuery;

@Component
public class NamuCharacterFinder {

	private final CharacterRepository characterRepository;

	public NamuCharacterFinder(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public NamuCharacter findNext(int level, int groupNumber, CharacterType type) {
		NextDeterminedDbQuery nextDeterminedDbQuery = new NextDeterminedDbQuery(level, groupNumber, type);
		NamuCharacter namuCharacter = characterRepository.findOrNull(nextDeterminedDbQuery);
		if (namuCharacter != null) {
			return namuCharacter;
		}
		throw NamuCharacterNotFoundException.notFoundNext();
	}

	public NamuCharacter findRandom(int level, int groupNumber, boolean isEndangered, CharacterType type) {
		NextRandomCharacterDbQuery nextRandomCharacterDbQuery
			= new NextRandomCharacterDbQuery(level, groupNumber, isEndangered, type);
		NamuCharacter namuCharacter = characterRepository.findRandomOrNull(nextRandomCharacterDbQuery);
		if (namuCharacter != null) {
			return namuCharacter;
		}
		throw NamuCharacterNotFoundException.notFoundRandom();
	}
}
