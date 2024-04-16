package univ.earthbreaker.namu.database.core.post;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {

	@Nullable PostJpaEntity findByNoAndMemberNo(long postNo, long memberNo);

	@Query("""
		SELECT p FROM PostJpaEntity p
		WHERE p.memberNo = :memberNo
		    AND p.createdAt BETWEEN :startDate AND :endDate""")
	@NotNull List<PostJpaEntity> findAllByMemberNoAndCreatedAtBetween(
		long memberNo,
		LocalDateTime startDate,
		LocalDateTime endDate
	);
}
