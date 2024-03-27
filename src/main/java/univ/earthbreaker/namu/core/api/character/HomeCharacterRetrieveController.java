package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.character.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.HomeCharacterRetrieveService;

@RestController
@AuthMapping
@RequestMapping("/v1/characters")
public class HomeCharacterRetrieveController {

	private final HomeCharacterRetrieveService homeCharacterRetrieveService;

	public HomeCharacterRetrieveController(HomeCharacterRetrieveService homeCharacterRetrieveService) {
		this.homeCharacterRetrieveService = homeCharacterRetrieveService;
	}

	@GetMapping("/home")
	public ResponseEntity<HomeCharacterResponse> retrieve(@LoginMember Long memberNo) {
		CurrentCharacter homeCharacter = homeCharacterRetrieveService.retrieveHomeCharacter(memberNo);
		return ResponseEntity.ok(HomeCharacterResponse.from(homeCharacter));
	}
}
