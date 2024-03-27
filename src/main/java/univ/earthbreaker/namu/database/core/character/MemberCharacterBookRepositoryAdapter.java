package univ.earthbreaker.namu.database.core.character;

import static univ.earthbreaker.namu.core.domain.character.book.CharacterBookMapper.CharacterSummaryInfoCollectionMapper;
import static univ.earthbreaker.namu.core.domain.character.book.CharacterBookMapper.CharacterSummaryInfoMapper;
import static univ.earthbreaker.namu.core.domain.character.book.CharacterBookMapper.CharactersMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.character.book.CharacterBook;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookMapper;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookRepository;

@Repository
public class MemberCharacterBookRepositoryAdapter implements CharacterBookRepository {

	private static final long DEFAULT_NO_IF_NOT_EXIST = 0L;
	private static final int ZERO = 0;

	private final MemberCharacterBookJpaRepository memberCharacterBookJpaRepository;

	public MemberCharacterBookRepositoryAdapter(MemberCharacterBookJpaRepository memberCharacterBookJpaRepository) {
		this.memberCharacterBookJpaRepository = memberCharacterBookJpaRepository;
	}

	@Override
	public @NotNull CharacterBook find(long memberNo) {
		List<MemberCharacterBookProjection> characterBookProjections
			= memberCharacterBookJpaRepository.findMemberCharacterBookByMemberNo(memberNo);

		MemberCharacterBookProjection projection = characterBookProjections.get(ZERO);

		return new CharacterBookMapper(
			projection.getNo().orElse(DEFAULT_NO_IF_NOT_EXIST),
			projection.getMemberNo().orElse(DEFAULT_NO_IF_NOT_EXIST),
			getCharactersMapper(characterBookProjections, projection.getTotalCountPerType())
		).mapping();
	}

	private @NotNull CharactersMapper getCharactersMapper(
		@NotNull List<MemberCharacterBookProjection> characterBookProjections,
		int totalCountPerType
	) {
		return new CharactersMapper(
			characterBookProjections.stream()
				.collect(Collectors.groupingBy(MemberCharacterBookProjection::getType))
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> new CharacterSummaryInfoCollectionMapper(
							totalCountPerType,
							entry.getValue().stream()
								.map(p -> new CharacterSummaryInfoMapper(
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
