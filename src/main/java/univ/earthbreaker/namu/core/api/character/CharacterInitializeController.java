package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacterInitializeService;

@RestController
@AuthMapping
@RequestMapping("/v1/characters/initialize")
public class CharacterInitializeController {

	private final CurrentCharacterInitializeService currentCharacterInitializeService;

	public CharacterInitializeController(CurrentCharacterInitializeService currentCharacterInitializeService) {
		this.currentCharacterInitializeService = currentCharacterInitializeService;
	}

	@PostMapping
	public ResponseEntity<Void> initializeAfterFinalCharacter(@LoginMember Long memberNo) {
		currentCharacterInitializeService.initialize(memberNo);
		return ResponseEntity.noContent().build();
	}
}
