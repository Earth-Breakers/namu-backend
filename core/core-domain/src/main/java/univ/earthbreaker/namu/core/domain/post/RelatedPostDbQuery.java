package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.Nullable;

public record RelatedPostDbQuery(
	long memberNo,
	long relatedMissionNo,
	int page,
	int size,
	@Nullable String sortKey
) {
}
