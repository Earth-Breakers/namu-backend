package univ.earthbreaker.namu.app.api.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.post.RelatedPostResult;
import univ.earthbreaker.namu.core.domain.post.RelatedPostRetrieveQuery;
import univ.earthbreaker.namu.core.service.post.PostRetrieveService;

@RestController
@RequestMapping("/v1/posts")
public class PostRetrieveController {

	private final PostRetrieveService postRetrieveService;

	public PostRetrieveController(PostRetrieveService postRetrieveService) {
		this.postRetrieveService = postRetrieveService;
	}

	@AuthMapping
	@GetMapping("/all")
	public ResponseEntity<List<PostFeedResponse>> retrieveAll(
		@LoginMember Long memberNo,
		@RequestBody PostDateRequest request
	) {
		List<PostFeedResponse> postFeedResponses = postRetrieveService.retrieveAll(request.toQuery(memberNo))
			.stream()
			.map(PostFeedResponse::from)
			.toList();
		return ResponseEntity.ok(postFeedResponses);
	}

	@AuthMapping
	@GetMapping("/related/{missionNo}")
	public ResponseEntity<RelatedPostFeedResponses> retrieveRelated(
		@LoginMember Long memberNo,
		@PathVariable Long missionNo,
		@RequestParam(name = "page") Integer page,
		@RequestParam(name = "size") Integer size,
		@RequestParam(name = "sort", required = false) String sortKey
	) {
		RelatedPostResult relatedPostResult = postRetrieveService
			.retrieveRelated(RelatedPostRetrieveQuery.of(memberNo, missionNo, page, size, sortKey));
		return ResponseEntity.ok(RelatedPostFeedResponses.from(relatedPostResult));
	}
}
