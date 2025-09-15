package univ.earthbreaker.namu.core.domain.character.book.infra;

import java.util.List;

import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;

public interface MemberCharacterRepository {

	MemberCharacter findOrNull(long memberNo, long characterNo);

	List<MemberCharacter> findByMemberNo(long memberNo);

	void createOrUpdate(AddFinalCharacterDbCommand command);
}
