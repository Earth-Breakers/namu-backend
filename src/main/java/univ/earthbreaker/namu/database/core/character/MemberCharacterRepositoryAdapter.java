package univ.earthbreaker.namu.database.core.character;

import static univ.earthbreaker.namu.core.domain.character.book.CharacterBookEventHandler.AddFinalCharacterDbCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacterRepository;

@Repository
public class MemberCharacterRepositoryAdapter implements MemberCharacterRepository {

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
			return memberCharacterJpaEntity.toMemberCharacter(characterJpaEntity, true);
		}
		return null;
	}

	@Override
	public @NotNull List<MemberCharacter> findByMemberNo(long memberNo) {
		Map<Long, MemberCharacterJpaEntity> memberCharacterJpaEntityMap
			= memberCharacterJpaRepository.findByMemberNo(memberNo)
			.stream()
			.collect(Collectors.toMap(
				MemberCharacterJpaEntity::getCharacterNo,
				Function.identity()
			));

		if (!memberCharacterJpaEntityMap.isEmpty()) {
			List<CharacterJpaEntity> characterJpaEntities = characterJpaRepository.findAll();
			List<Long> acquiredCharacterNos = new ArrayList<>(memberCharacterJpaEntityMap.keySet());

			return characterJpaEntities.stream()
				.map(characterJpaEntity -> {
					Long characterNo = characterJpaEntity.getNo();
					if (acquiredCharacterNos.contains(characterNo)) {
						return memberCharacterJpaEntityMap.get(characterNo)
							.toMemberCharacter(characterJpaEntity, true);
					} else {
						return MemberCharacter.notAcquired(memberNo, characterJpaEntity.toNamuCharacter());
					}
				})
				.toList();
		}
		return Collections.emptyList();
	}

	@Override
	public void createOrUpdate(@NotNull AddFinalCharacterDbCommand command) {
		MemberCharacterJpaEntity memberCharacterJpaEntity = memberCharacterJpaRepository
			.findByMemberNoAndCharacterNo(command.memberNo(), command.characterNo());
		if (memberCharacterJpaEntity != null) {
			memberCharacterJpaEntity.plusOneCount();
		} else {
			memberCharacterJpaRepository.save(
				MemberCharacterJpaEntity.initialize(command.memberNo(), command.characterNo())
			);
		}
	}
}
