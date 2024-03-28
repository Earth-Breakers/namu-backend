package univ.earthbreaker.namu.database.core.character;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.character.book.CharacterBook;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookMapper;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacterRepository;

@Repository
public class MemberCharacterRepositoryAdapter implements MemberCharacterRepository {

	private static final long DEFAULT_NO_IF_NOT_EXIST = 0L;
	private static final int ZERO = 0;

	private final MemberCharacterJpaRepository memberCharacterJpaRepository;
	private final CharacterJpaRepository characterJpaRepository;

	public MemberCharacterRepositoryAdapter(
		MemberCharacterJpaRepository memberCharacterJpaRepository,
		CharacterJpaRepository characterJpaRepository
	) {
		this.memberCharacterJpaRepository = memberCharacterJpaRepository;
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public @Nullable MemberCharacter findOrNull(long memberNo, long characterNo) {
		MemberCharacterJpaEntity memberCharacterJpaEntity = memberCharacterJpaRepository
			.findByMemberNoAndCharacterNo(memberNo, characterNo);
		if (memberCharacterJpaEntity != null) {
			CharacterJpaEntity characterJpaEntity = characterJpaRepository.findByNo(characterNo);
			return memberCharacterJpaEntity.toMemberCharacter(characterJpaEntity);
		}
		return null;
	}

	@Override
	public @NotNull CharacterBook findBook(long memberNo) {
		List<MemberCharacterBookProjection> characterBookProjections
			= memberCharacterJpaRepository.findMemberCharacterBookByMemberNo(memberNo);

		MemberCharacterBookProjection projection = characterBookProjections.get(ZERO);

		return new CharacterBookMapper(
			projection.getMemberNo().orElse(memberNo),
			getCharactersMapper(characterBookProjections, projection.getTotalCountPerType())
		).mapping();
	}

	private @NotNull CharacterBookMapper.CharactersMapper getCharactersMapper(
		@NotNull List<MemberCharacterBookProjection> characterBookProjections,
		int totalCountPerType
	) {
		return new CharacterBookMapper.CharactersMapper(
			characterBookProjections.stream()
				.collect(Collectors.groupingBy(MemberCharacterBookProjection::getType))
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> new CharacterBookMapper.CharacterSummaryInfoCollectionMapper(
							totalCountPerType,
							entry.getValue().stream()
								.map(p -> new CharacterBookMapper.CharacterSummaryInfoMapper(
									p.getCharacterNo().orElse(DEFAULT_NO_IF_NOT_EXIST),
									p.getCount().orElse(ZERO),
									p.getThumbnailImagePath(),
									p.getIsAcquired())
								)
								.toList()
						)
					)
				)
		);
	}
}
