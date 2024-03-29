package univ.earthbreaker.namu.core.domain.character.book;

import static univ.earthbreaker.namu.core.domain.character.book.CharacterBookEventHandler.AddFinalCharacterDbCommand;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCharacterRepository {

	@Nullable MemberCharacter findOrNull(long memberNo, long characterNo);

	@NotNull CharacterBook findBook(long memberNo);

	void createOrUpdate(AddFinalCharacterDbCommand command);
}
