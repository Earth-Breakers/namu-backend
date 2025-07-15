package univ.earthbreaker.namu.app.api.post;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.post.PostRetrieveAllQuery;

public record PostDateRequest(
	LocalDate postDate
) {
	@NotNull PostRetrieveAllQuery toQuery(Long memberNo) {
		return new PostRetrieveAllQuery(memberNo, postDate);
	}
}
