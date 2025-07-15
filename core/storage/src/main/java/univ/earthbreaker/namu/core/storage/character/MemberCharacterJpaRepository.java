package univ.earthbreaker.namu.core.storage.character;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCharacterJpaRepository extends JpaRepository<MemberCharacterJpaEntity, Long> {

	@Nullable MemberCharacterJpaEntity findByMemberNoAndCharacterNo(long memberNo, long characterNo);

	List<MemberCharacterJpaEntity> findByMemberNo(long memberNo);
}
