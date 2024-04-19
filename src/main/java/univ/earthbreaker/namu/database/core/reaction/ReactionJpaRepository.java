package univ.earthbreaker.namu.database.core.reaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionJpaRepository extends JpaRepository<ReactionJpaEntity, Long> {

	Boolean existsByMemberNoAndReactionTarget(long memberNo, ReactionJpaEntity.ReactionTarget target);

	void deleteByMemberNoAndReactionTarget(long memberNo, ReactionJpaEntity.ReactionTarget target);
}
