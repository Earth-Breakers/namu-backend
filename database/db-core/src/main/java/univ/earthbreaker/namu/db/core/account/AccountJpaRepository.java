package univ.earthbreaker.namu.db.core.account;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {

	@Nullable AccountJpaEntity findBySocialId(String socialId);
}
