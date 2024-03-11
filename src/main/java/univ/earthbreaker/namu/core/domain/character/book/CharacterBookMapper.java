package univ.earthbreaker.namu.core.domain.character.book;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.character.CharacterType;

public class CharacterBookMapper {

	private final long characterBookNo;
	private final long memberNo;
	private final CharactersMapper charactersMapper;

	public CharacterBookMapper(long characterBookNo, long memberNo, CharactersMapper charactersMapper) {
		this.characterBookNo = characterBookNo;
		this.memberNo = memberNo;
		this.charactersMapper = charactersMapper;
	}

	public CharacterBook mapping() {
		return new CharacterBook(characterBookNo, memberNo, charactersMapper.mapping());
	}

	public record CharactersMapper(
		Map<CharacterType, CharacterSummaryInfoCollectionMapper> values
	) {
		@NotNull Characters mapping() {
			return new Characters(
				values.entrySet()
					.stream()
					.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> entry.getValue().mapping()
					))
			);
		}
	}

	public record CharacterSummaryInfoCollectionMapper(
		int totalCountOfType,
		List<CharacterSummaryInfoMapper> summaryInfoMappers
	) {
		@NotNull CharacterSummaryInfoCollection mapping() {
			return new CharacterSummaryInfoCollection(
				totalCountOfType,
				summaryInfoMappers.stream()
					.map(CharacterSummaryInfoMapper::mapping)
					.toList()
			);
		}
	}

	public record CharacterSummaryInfoMapper(
		long characterNo,
		int count,
		String thumbnailImagePath,
		boolean isAcquired
	) {
		@NotNull CharacterSummaryInfo mapping() {
			return new CharacterSummaryInfo(characterNo, count, thumbnailImagePath, isAcquired);
		}
	}
}
