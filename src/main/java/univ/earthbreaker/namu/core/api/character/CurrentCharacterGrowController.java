package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.character.CurrentCharacterGrowResult;
import univ.earthbreaker.namu.core.domain.character.CurrentCharacterGrowService;

@RestController
@RequestMapping("/v1/character/grow")
public class CurrentCharacterGrowController {

	private final CurrentCharacterGrowService currentCharacterGrowService;

	public CurrentCharacterGrowController(CurrentCharacterGrowService currentCharacterGrowService) {
		this.currentCharacterGrowService = currentCharacterGrowService;
	}

	@AuthMapping
	@PostMapping("/next")
	public ResponseEntity<CurrentCharacterGrowResult> growToNextLevelCharacter(@LoginMember long memberNo) {
		return ResponseEntity.ok(currentCharacterGrowService.growToNextLevel(memberNo));
	}

	@AuthMapping
	@PostMapping("/random")
	public ResponseEntity<CurrentCharacterGrowResult> growToNextLevelRandomCharacter(@LoginMember long memberNo) {
		return ResponseEntity.ok(currentCharacterGrowService.growToNextRandom(memberNo));
	}
}
