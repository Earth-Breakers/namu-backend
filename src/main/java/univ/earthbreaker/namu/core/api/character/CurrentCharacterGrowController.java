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

	@PostMapping("/next")
	public ResponseEntity<Void> growToNextLevelCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToNextLevel(memberNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/random")
	public ResponseEntity<Void> growToNextLevelRandomCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToNextRandom(memberNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/final")
	public ResponseEntity<Void> growToRandomFinalCharacter(@LoginMember Long memberNo) {
		currentCharacterGrowService.growToFinal(memberNo);
		return ResponseEntity.noContent().build();
	}
}
