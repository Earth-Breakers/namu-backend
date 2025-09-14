package univ.earthbreaker.namu.app.api.character;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.domain.character.CharacterType;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;
import univ.earthbreaker.namu.app.support.AuthMapping;
import univ.earthbreaker.namu.app.support.LoginMember;
import univ.earthbreaker.namu.core.service.character.book.CharacterBookDetailReadService;
import univ.earthbreaker.namu.core.service.character.book.MemberCharacterFinder;

@RestController
@RequestMapping("/v1/characters/books")
public class CharacterBookReadController {

	private final MemberCharacterFinder memberCharacterFinder;
	private final CharacterBookDetailReadService characterBookDetailReadService;

	public CharacterBookReadController(
		MemberCharacterFinder memberCharacterFinder,
		CharacterBookDetailReadService characterBookDetailReadService
	) {
		this.memberCharacterFinder = memberCharacterFinder;
		this.characterBookDetailReadService = characterBookDetailReadService;
	}

	@AuthMapping
	@GetMapping("/all")
	public ResponseEntity<CharacterBookResponse> readAll(@LoginMember Long memberNo) {
		CharacterBookView bookView = new CharacterBookView(memberCharacterFinder.findAllBy(memberNo));
		List<CharacterBookResponse.BookSectionResponse> bookSectionResponses = Arrays.stream(CharacterType.values())
			.map(type -> CharacterBookViewReader.readByType(bookView, type))
			.toList();
		return ResponseEntity.ok(new CharacterBookResponse(
			bookView.readTotalAcquiredCharacterCount(),
			bookSectionResponses
		));
	}

	@AuthMapping
	@GetMapping("/detail/{characterNo}")
	public ResponseEntity<CharacterDetailResponse> readDetail(
		@LoginMember Long memberNo,
		@PathVariable Long characterNo
	) {
		MemberCharacter memberCharacter = characterBookDetailReadService.retrieveDetail(memberNo, characterNo);
		return ResponseEntity.ok(CharacterDetailResponse.from(memberCharacter));
	}
}
