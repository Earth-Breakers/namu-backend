package univ.earthbreaker.namu.database.core.character;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public interface MemberCharacterBookProjection {

	Optional<Long> getNo();

	Optional<Long> getMemberNo();

	Optional<Long> getCharacterNo();

	Optional<Integer> getCount();

	@NotNull CharacterType getType();

	@NotNull String getThumbnailImagePath();

	@NotNull Integer getTotalCountPerType();

	@NotNull Boolean getIsAcquired();
}
