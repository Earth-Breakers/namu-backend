package univ.earthbreaker.namu.app.api.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.domain.post.PostReactionResult;
import univ.earthbreaker.namu.core.domain.post.PostRetrieveDetailQuery;
import univ.earthbreaker.namu.core.service.post.PostDetailRetrieveService;

@RestController
@RequestMapping("/v1/posts")
public class PostDetailRetrieveController {

	private final PostDetailRetrieveService postDetailRetrieveService;

	public PostDetailRetrieveController(PostDetailRetrieveService postDetailRetrieveService) {
		this.postDetailRetrieveService = postDetailRetrieveService;
	}

	@AuthMapping
	@GetMapping("/detail/{postNo}")
	public ResponseEntity<PostDetailResponse> retrieveDetail(@LoginMember Long memberNo, @PathVariable Long postNo) {
		PostReactionResult result = postDetailRetrieveService.retrieveDetail(new PostRetrieveDetailQuery(memberNo, postNo));
		return ResponseEntity.ok(PostDetailResponse.of(result.post(), result.reactionStatus()));
	}
}
