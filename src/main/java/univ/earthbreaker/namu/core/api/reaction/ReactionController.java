package univ.earthbreaker.namu.core.api.reaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.reaction.ReactionService;

@RestController
@RequestMapping("/v1/reactions")
public class ReactionController {

	private final ReactionService reactionService;

	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}

	@AuthMapping
	@PostMapping("/do-reaction")
	public ResponseEntity<Void> doReaction(@LoginMember Long memberNo, @RequestBody ReactionRequest request) {
		reactionService.doReaction(request.toCommand(memberNo));
		return ResponseEntity.noContent().build();
	}

	@AuthMapping
	@PostMapping("/undo-reaction")
	public ResponseEntity<Void> undoReaction(@LoginMember Long memberNo, @RequestBody ReactionRequest request) {
		reactionService.undoReaction(request.toCommand(memberNo));
		return ResponseEntity.noContent().build();
	}
}
