package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.character.book.BookResult;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookReadService;

@RestController
@RequestMapping("/v1/character/book")
public class CharacterBookReadController {

	private final CharacterBookReadService characterBookReadService;

	public CharacterBookReadController(CharacterBookReadService characterBookReadService) {
		this.characterBookReadService = characterBookReadService;
	}

	@AuthMapping
	@GetMapping
	public ResponseEntity<BookResult> readAll(@LoginMember Long memberNo) {
		return ResponseEntity.ok(characterBookReadService.readAll(memberNo));
	}
}
