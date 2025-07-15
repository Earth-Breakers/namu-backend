package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.domain.character.HomeCharacterRetrieveService;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.support.AuthMapping;
import univ.earthbreaker.namu.core.support.LoginMember;

@RestController
@RequestMapping("/v1/characters")
public class HomeCharacterRetrieveController {

	private final HomeCharacterRetrieveService homeCharacterRetrieveService;

	public HomeCharacterRetrieveController(HomeCharacterRetrieveService homeCharacterRetrieveService) {
		this.homeCharacterRetrieveService = homeCharacterRetrieveService;
	}

	@AuthMapping
	@GetMapping("/home")
	public ResponseEntity<HomeCharacterResponse> retrieve(@LoginMember Long memberNo) {
		CurrentCharacter homeCharacter = homeCharacterRetrieveService.retrieveHomeCharacter(memberNo);
		return ResponseEntity.ok(HomeCharacterResponse.from(homeCharacter));
	}
}
