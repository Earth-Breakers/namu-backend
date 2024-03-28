package univ.earthbreaker.namu.core.api.character;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookDetailReadService;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;
import univ.earthbreaker.namu.core.domain.character.book.BookResult;
import univ.earthbreaker.namu.core.domain.character.book.CharacterBookReadService;

@RestController
@AuthMapping
@RequestMapping("/v1/characters/books")
public class CharacterBookReadController {

	private final CharacterBookReadService characterBookReadService;
	private final CharacterBookDetailReadService characterBookDetailReadService;

	public CharacterBookReadController(
		CharacterBookReadService characterBookReadService,
		CharacterBookDetailReadService characterBookDetailReadService
	) {
		this.characterBookReadService = characterBookReadService;
		this.characterBookDetailReadService = characterBookDetailReadService;
	}

	@GetMapping("/all")
	public ResponseEntity<BookResult> readAll(@LoginMember Long memberNo) {
		return ResponseEntity.ok(characterBookReadService.readAll(memberNo));
	}

	@GetMapping("/detail/{characterNo}")
	public ResponseEntity<CharacterDetailResponse> readDetail(
		@LoginMember Long memberNo,
		@PathVariable Long characterNo
	) {
		MemberCharacter memberCharacter = characterBookDetailReadService.retrieveDetail(memberNo, characterNo);
		return ResponseEntity.ok(CharacterDetailResponse.from(memberCharacter));
	}
}
