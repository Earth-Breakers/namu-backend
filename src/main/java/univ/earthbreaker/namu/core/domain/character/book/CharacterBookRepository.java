package univ.earthbreaker.namu.core.domain.character.book;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterBookRepository {

	@NotNull CharacterBook find(long memberNo);
}
