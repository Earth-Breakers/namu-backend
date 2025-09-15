package univ.earthbreaker.namu.app.api.post;

import java.time.LocalDate;

import univ.earthbreaker.namu.core.domain.post.PostRetrieveAllQuery;

public record PostDateRequest(LocalDate postDate) {
	PostRetrieveAllQuery toQuery(Long memberNo) {
		return new PostRetrieveAllQuery(memberNo, postDate);
	}
}
