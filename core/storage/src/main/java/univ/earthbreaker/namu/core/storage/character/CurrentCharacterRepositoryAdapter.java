package univ.earthbreaker.namu.core.storage.character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterRepository;

@Repository
public class CurrentCharacterRepositoryAdapter implements CurrentCharacterRepository {

	private final CurrentCharacterJpaRepository currentCharacterJpaRepository;
	private final CharacterJpaRepository characterJpaRepository;

	public CurrentCharacterRepositoryAdapter(
		CurrentCharacterJpaRepository currentCharacterJpaRepository,
		CharacterJpaRepository characterJpaRepository
	) {
		this.currentCharacterJpaRepository = currentCharacterJpaRepository;
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public @Nullable CurrentCharacter findOrNull(long memberNo) {
		CurrentCharacterJpaEntity currentCharacterJpaEntity = currentCharacterJpaRepository.findByMemberNo(memberNo);
		if (currentCharacterJpaEntity != null) {
			return currentCharacterJpaEntity.toCurrentCharacter();
		}
		return null;
	}

	@Override
	public @NotNull CurrentCharacter register(long memberNo) {
		CurrentCharacterJpaEntity currentCharacterJpaEntity = initializeCurrentCharacterJpaEntity(memberNo);
		return currentCharacterJpaRepository.save(currentCharacterJpaEntity).toInitCurrentCharacter();
	}

	@Override
	public void updateToInitial(long memberNo) {
		CurrentCharacterJpaEntity currentCharacterJpaEntity = initializeCurrentCharacterJpaEntity(memberNo);
		currentCharacterJpaRepository.updateCurrentCharacter(
			currentCharacterJpaEntity.getCharacterNo(),
			currentCharacterJpaEntity.getCharacterType(),
			currentCharacterJpaEntity.getLevel(),
			currentCharacterJpaEntity.getRequiredExp(),
			currentCharacterJpaEntity.getCurrentExp(),
			currentCharacterJpaEntity.getMainImagePath(),
			currentCharacterJpaEntity.getBackgroundImagePath(),
			currentCharacterJpaEntity.getScripts(),
			memberNo
		);
	}

	private @NotNull CurrentCharacterJpaEntity initializeCurrentCharacterJpaEntity(long memberNo) {
		CharacterProjection characterInitialProjection = characterJpaRepository.findByType(CharacterType.DEFAULT);
		return CurrentCharacterJpaEntity.initialize(characterInitialProjection, memberNo);
	}

	@Override
	public void update(@NotNull CurrentCharacter updatedCurrentCharacter) {
		currentCharacterJpaRepository.updateCurrentCharacter(
			updatedCurrentCharacter.getTargetCharacterNo(),
			updatedCurrentCharacter.getCharacterType(),
			updatedCurrentCharacter.getStatusLevel(),
			updatedCurrentCharacter.getStatusRequiredExp(),
			updatedCurrentCharacter.getStatusCurrentExp(),
			updatedCurrentCharacter.getTargetCharacterMainImage(),
			updatedCurrentCharacter.getTargetCharacterBackgroundImage(),
			updatedCurrentCharacter.getTargetCharacterScripts(),
			updatedCurrentCharacter.getMasterNo()
		);
	}
}
