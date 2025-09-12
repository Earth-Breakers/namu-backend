package univ.earthbreaker.namu.core.storage.reaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionJpaRepository extends JpaRepository<ReactionJpaEntity, Long> {

	Boolean existsByMemberNoAndReactionTarget(long memberNo, ReactionJpaEntity.ReactionTarget target);

	void deleteByMemberNoAndReactionTarget(long memberNo, ReactionJpaEntity.ReactionTarget target);

	List<ReactionJpaEntity> findAllByMemberNoAndReactionTarget(long memberNo, ReactionJpaEntity.ReactionTarget target);
}
