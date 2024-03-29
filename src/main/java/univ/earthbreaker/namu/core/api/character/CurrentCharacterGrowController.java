package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterGrowService;

@RestController
@AuthMapping
@RequestMapping("/v1/characters/grow")
public class CurrentCharacterGrowController {

	private final CurrentCharacterGrowService currentCharacterGrowService;

	public CurrentCharacterGrowController(CurrentCharacterGrowService currentCharacterGrowService) {
		this.currentCharacterGrowService = currentCharacterGrowService;
	}

	@PostMapping("/end")
	public ResponseEntity<Void> growToEndLevelCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToEndLevel(memberNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/middle")
	public ResponseEntity<Void> growToMiddleLevelCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToMiddleLevel(memberNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/final")
	public ResponseEntity<Void> growToRandomFinalCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToFinalRandom(memberNo);
		return ResponseEntity.noContent().build();
	}
}
