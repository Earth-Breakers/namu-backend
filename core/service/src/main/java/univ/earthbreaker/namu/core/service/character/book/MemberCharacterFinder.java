package univ.earthbreaker.namu.core.service.character.book;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacterNotFoundException;
import univ.earthbreaker.namu.core.domain.character.book.infra.MemberCharacterRepository;

@Component
public class MemberCharacterFinder {

	private final MemberCharacterRepository memberCharacterRepository;

	public MemberCharacterFinder(MemberCharacterRepository memberCharacterRepository) {
		this.memberCharacterRepository = memberCharacterRepository;
	}

	@NotNull MemberCharacter find(long memberNo, long characterNo) {
		MemberCharacter memberCharacter = memberCharacterRepository.findOrNull(memberNo, characterNo);
		if (memberCharacter != null) {
			return memberCharacter;
		}
		throw MemberCharacterNotFoundException.notFound(characterNo);
	}

	public @NotNull List<MemberCharacter> findAllBy(long memberNo) {
		return memberCharacterRepository.findByMemberNo(memberNo);
	}
}
