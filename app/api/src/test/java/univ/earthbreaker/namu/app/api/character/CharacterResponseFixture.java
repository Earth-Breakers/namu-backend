package univ.earthbreaker.namu.app.api.character;

import static univ.earthbreaker.namu.app.api.character.CharacterBookResponse.BookSectionResponse;
import static univ.earthbreaker.namu.app.api.character.CharacterBookResponse.ProfileResponse;
import static univ.earthbreaker.namu.core.domain.character.CharacterFixture.*;
import static univ.earthbreaker.namu.core.domain.character.CharacterType.BEAUTY;
import static univ.earthbreaker.namu.core.domain.character.CharacterType.PURIFY;
import static univ.earthbreaker.namu.core.domain.character.CharacterType.VITALITY;

import java.util.List;

import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;

public class CharacterResponseFixture {

	/**
	 * 캐릭터 도감 FIXTURE
	 */
	public static final int TOTAL_ACQUIRED_COUNT = 3;
	public static final int TOTAL_COUNT_OF_TYPE = 2;
	public static final int ACQUIRED_COUNT = 1;

	public static final ProfileResponse ACQUIRED_PROFILE_RESPONSE
		= new ProfileResponse(CHARACTER_NO, TEST_CHARACTER_IMAGE_URL, true);
	public static final ProfileResponse NOT_ACQUIRED_PROFILE_RESPONSE
		= new ProfileResponse(0, TEST_CHARACTER_IMAGE_URL, false);

	public static final BookSectionResponse BEAUTY_SECTION_RESPONSE
		= new BookSectionResponse(BEAUTY, TOTAL_COUNT_OF_TYPE, ACQUIRED_COUNT, List.of(ACQUIRED_PROFILE_RESPONSE, NOT_ACQUIRED_PROFILE_RESPONSE));
	public static final BookSectionResponse PURIFY_SECTION_RESPONSE
		= new BookSectionResponse(PURIFY, TOTAL_COUNT_OF_TYPE, ACQUIRED_COUNT, List.of(ACQUIRED_PROFILE_RESPONSE, NOT_ACQUIRED_PROFILE_RESPONSE));
	public static final BookSectionResponse VITALITY_SECTION_RESPONSE
		= new BookSectionResponse(VITALITY, TOTAL_COUNT_OF_TYPE, ACQUIRED_COUNT, List.of(ACQUIRED_PROFILE_RESPONSE, NOT_ACQUIRED_PROFILE_RESPONSE));

	public static final CharacterBookResponse BOOK_RESPONSE = new CharacterBookResponse(
		TOTAL_ACQUIRED_COUNT,
		List.of(BEAUTY_SECTION_RESPONSE, PURIFY_SECTION_RESPONSE, VITALITY_SECTION_RESPONSE)
	);

	public static final MemberCharacter MEMBER_CHARACTER_1 = new MemberCharacter(1, MEMBER_NO, 1, END_NAMU_CHARACTER, true);
	public static final MemberCharacter MEMBER_CHARACTER_2 = new MemberCharacter(1, MEMBER_NO, 0, END_NAMU_CHARACTER, false);
	public static final MemberCharacter MEMBER_CHARACTER_3 = new MemberCharacter(1, MEMBER_NO, 1, END_PURIFY_NAMU_CHARACTER, true);
	public static final MemberCharacter MEMBER_CHARACTER_4 = new MemberCharacter(1, MEMBER_NO, 0, END_PURIFY_NAMU_CHARACTER, false);
	public static final MemberCharacter MEMBER_CHARACTER_5 = new MemberCharacter(1, MEMBER_NO, 1, END_VITALITY_NAMU_CHARACTER, true);
	public static final MemberCharacter MEMBER_CHARACTER_6 = new MemberCharacter(1, MEMBER_NO, 0, END_VITALITY_NAMU_CHARACTER, true);
	public static final List<MemberCharacter> MEMBER_CHARACTERS = List.of(MEMBER_CHARACTER_1, MEMBER_CHARACTER_2, MEMBER_CHARACTER_3, MEMBER_CHARACTER_4, MEMBER_CHARACTER_5, MEMBER_CHARACTER_6);
}
