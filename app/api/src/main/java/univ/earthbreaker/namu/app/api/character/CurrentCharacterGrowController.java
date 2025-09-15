package univ.earthbreaker.namu.app.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.service.character.current.CurrentCharacterGrowService;

@RestController
@RequestMapping("/v1/characters/grow")
public class CurrentCharacterGrowController {

	private final CurrentCharacterGrowService currentCharacterGrowService;

	public CurrentCharacterGrowController(CurrentCharacterGrowService currentCharacterGrowService) {
		this.currentCharacterGrowService = currentCharacterGrowService;
	}

	@AuthMapping
	@PostMapping("/end")
	public ResponseEntity<Void> growToEndLevelCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToEndLevel(memberNo);
		return ResponseEntity.noContent().build();
	}

	@AuthMapping
	@PostMapping("/middle")
	public ResponseEntity<Void> growToMiddleLevelCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToMiddleLevel(memberNo);
		return ResponseEntity.noContent().build();
	}

	@AuthMapping
	@PostMapping("/final")
	public ResponseEntity<FinalCharacterResponse> growToRandomFinalCharacter(@LoginMember Long memberNo) {
		return ResponseEntity.ok(FinalCharacterResponse.from(currentCharacterGrowService.growToFinalRandom(memberNo)));
	}
}
