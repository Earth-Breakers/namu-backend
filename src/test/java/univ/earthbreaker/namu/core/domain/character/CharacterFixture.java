package univ.earthbreaker.namu.core.domain.character;

import static univ.earthbreaker.namu.core.domain.character.CharacterType.BEAUTY;
import static univ.earthbreaker.namu.core.domain.character.CharacterType.INITIAL;
import static univ.earthbreaker.namu.core.domain.character.CharacterType.PURIFY;
import static univ.earthbreaker.namu.core.domain.character.CharacterType.VITALITY;

import java.util.List;

import univ.earthbreaker.namu.core.domain.character.book.BookResult;
import univ.earthbreaker.namu.core.domain.character.book.BookSectionResult;
import univ.earthbreaker.namu.core.domain.character.book.CharacterProfileResult;
import univ.earthbreaker.namu.core.domain.character.book.MemberCharacter;
import univ.earthbreaker.namu.core.domain.character.current.CharacterStatus;
import univ.earthbreaker.namu.core.domain.character.current.CurrentCharacter;
import univ.earthbreaker.namu.core.domain.character.current.Level;
import univ.earthbreaker.namu.core.domain.character.current.Master;
import univ.earthbreaker.namu.core.domain.character.current.TargetCharacter;

public class CharacterFixture {

	public static final long MEMBER_NO = 1L;
	public static final long CHARACTER_NO = 1L;
	static final int GROUP_NO = 1;
	public static final String NAME = "characterName";
	public static final String CHARACTER_IMAGE_PATH = "/character-dir/test_image.png";
	static final CharacterType CHARACTER_TYPE = BEAUTY;

	public static final int INITIAL_EXP = 0;
	public static final int BEGIN_REQUIRED_EXP = 3;
	public static final int BEGIN_LEVEL_VALUE = 1;

	public static final int MIDDLE_REQUIRED_EXP = 5;
	public static final int MIDDLE_LEVEL_VALUE = 2;

	public static final int END_REQUIRED_EXP = 10;
	public static final int END_LEVEL_VALUE = 3;

	public static final CharacterStatus BEGIN_STATUS = CharacterStatus.initialize(BEGIN_REQUIRED_EXP);
	public static final CharacterStatus MIDDLE_STATUS = CharacterStatus.of(MIDDLE_LEVEL_VALUE, MIDDLE_REQUIRED_EXP, INITIAL_EXP);
	public static final CharacterStatus END_STATUS = CharacterStatus.of(END_LEVEL_VALUE, END_REQUIRED_EXP, INITIAL_EXP);

	public static final CurrentCharacter BEGIN_CURRENT_CHARACTER = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		INITIAL,
		BEGIN_STATUS
	);

	public static final CurrentCharacter BEGIN_CURRENT_CHARACTER_WITH_MAX_EXP = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		CHARACTER_TYPE,
		CharacterStatus.of(BEGIN_LEVEL_VALUE, BEGIN_REQUIRED_EXP, BEGIN_REQUIRED_EXP)
	);

	public static final CurrentCharacter MIDDLE_CURRENT_CHARACTER = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		CHARACTER_TYPE,
		MIDDLE_STATUS
	);

	public static final CurrentCharacter MIDDLE_CURRENT_CHARACTER_WITH_MAX_EXP = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		CHARACTER_TYPE,
		CharacterStatus.of(MIDDLE_LEVEL_VALUE, MIDDLE_REQUIRED_EXP, MIDDLE_REQUIRED_EXP)
	);

	public static final CurrentCharacter END_CURRENT_CHARACTER = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		CHARACTER_TYPE,
		END_STATUS
	);

	public static final CurrentCharacter END_CURRENT_CHARACTER_WITH_MAX_EXP = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		CHARACTER_TYPE,
		CharacterStatus.of(END_LEVEL_VALUE, END_REQUIRED_EXP, END_REQUIRED_EXP)
	);

	public static final NamuCharacter BEGIN_NAMU_CHARACTER = NamuCharacter.builder()
		.no(Long.MIN_VALUE)
		.type(INITIAL)
		.gender(Gender.MALE)
		.isEndangered(true)
		.groupNumber(GROUP_NO)
		.level(Level.BEGIN.getValue())
		.requiredExp(BEGIN_REQUIRED_EXP)
		.name("beginName")
		.description("beginDescription")
		.thumbnailImagePath(CHARACTER_IMAGE_PATH)
		.mainImagePath(CHARACTER_IMAGE_PATH)
		.build();

	public static final NamuCharacter MIDDLE_NAMU_CHARACTER = NamuCharacter.builder()
		.no(Long.MAX_VALUE / 2)
		.type(CHARACTER_TYPE)
		.gender(Gender.MALE)
		.isEndangered(true)
		.groupNumber(GROUP_NO)
		.level(Level.MIDDLE.getValue())
		.requiredExp(MIDDLE_REQUIRED_EXP)
		.name("middleName")
		.description("middleDescription")
		.thumbnailImagePath(CHARACTER_IMAGE_PATH)
		.mainImagePath(CHARACTER_IMAGE_PATH)
		.build();

	public static final NamuCharacter END_NAMU_CHARACTER = NamuCharacter.builder()
		.no(Long.MAX_VALUE)
		.type(CHARACTER_TYPE)
		.gender(Gender.MALE)
		.isEndangered(true)
		.groupNumber(GROUP_NO)
		.level(Level.END.getValue())
		.requiredExp(END_REQUIRED_EXP)
		.name("endName")
		.description("endDescription")
		.thumbnailImagePath(CHARACTER_IMAGE_PATH)
		.mainImagePath(CHARACTER_IMAGE_PATH)
		.build();

	/**
	 * 캐릭터 도감 FIXTURE
	 */
	public static final int TOTAL_ACQUIRED_COUNT = 3;
	public static final int TOTAL_COUNT_OF_TYPE = 2;
	public static final int ACQUIRED_COUNT = 1;

	public static final CharacterProfileResult ACQUIRED_PROFILE_RESULT
		= new CharacterProfileResult(CHARACTER_NO, CHARACTER_IMAGE_PATH, true);
	public static final CharacterProfileResult NOT_ACQUIRED_PROFILE_RESULT
		= new CharacterProfileResult(0, CHARACTER_IMAGE_PATH, false);

	public static final BookSectionResult BEAUTY_SECTION_RESULT
		= new BookSectionResult(BEAUTY, TOTAL_COUNT_OF_TYPE, ACQUIRED_COUNT, List.of(ACQUIRED_PROFILE_RESULT, NOT_ACQUIRED_PROFILE_RESULT));
	public static final BookSectionResult PURIFY_SECTION_RESULT
		= new BookSectionResult(PURIFY, TOTAL_COUNT_OF_TYPE, ACQUIRED_COUNT, List.of(ACQUIRED_PROFILE_RESULT, NOT_ACQUIRED_PROFILE_RESULT));
	public static final BookSectionResult VITALITY_SECTION_RESULT
		= new BookSectionResult(VITALITY, TOTAL_COUNT_OF_TYPE, ACQUIRED_COUNT, List.of(ACQUIRED_PROFILE_RESULT, NOT_ACQUIRED_PROFILE_RESULT));

	public static final BookResult BOOK_RESULT = new BookResult(
		TOTAL_ACQUIRED_COUNT,
		List.of(BEAUTY_SECTION_RESULT, PURIFY_SECTION_RESULT, VITALITY_SECTION_RESULT)
	);

	public static final MemberCharacter MEMBER_CHARACTER = new MemberCharacter(1, MEMBER_NO, 5, END_NAMU_CHARACTER);
}
