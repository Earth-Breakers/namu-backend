package univ.earthbreaker.namu.core.api.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveDetailQuery;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveService;

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
	@GetMapping("/{postNo}")
	public ResponseEntity<PostDetailResponse> retrieveDetail(@LoginMember Long memberNo, @PathVariable Long postNo) {
		Post post = postRetrieveService.retrieve(new PostRetrieveDetailQuery(memberNo, postNo));
		return ResponseEntity.ok(PostDetailResponse.from(post));
	}
}
