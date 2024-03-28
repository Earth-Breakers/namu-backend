package univ.earthbreaker.namu.core.domain.character;

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
	static final CharacterType CHARACTER_TYPE = CharacterType.BEAUTY;

	public static final int INITIAL_EXP = 0;
	public static final int BEGIN_REQUIRED_EXP = 3;
	public static final int BEGIN_LEVEL_VALUE = 1;

	public static final int MIDDLE_REQUIRED_EXP = 5;
	public static final int MIDDLE_LEVEL_VALUE = 2;

	public static final int END_REQUIRED_EXP = 10;
	public static final int END_LEVEL_VALUE = 3;

	public static final CharacterStatus BEGIN_STATUS = CharacterStatus.initialize(BEGIN_REQUIRED_EXP);
	public static final CharacterStatus MIDDLE_STATUS = CharacterStatus.of(MIDDLE_LEVEL_VALUE, MIDDLE_REQUIRED_EXP,
		INITIAL_EXP);
	public static final CharacterStatus END_STATUS = CharacterStatus.of(END_LEVEL_VALUE, END_REQUIRED_EXP, INITIAL_EXP);

	public static final CurrentCharacter BEGIN_CURRENT_CHARACTER = new CurrentCharacter(
		new Master(MEMBER_NO),
		new TargetCharacter(CHARACTER_NO, GROUP_NO, NAME, CHARACTER_IMAGE_PATH),
		CharacterType.INITIAL,
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
		.type(CharacterType.INITIAL)
		.gender(Gender.MALE)
		.isEndangered(true)
		.groupNumber(GROUP_NO)
		.level(Level.BEGIN.getValue())
		.requiredExp(BEGIN_REQUIRED_EXP)
		.name("beginName")
		.description("beginDescription")
		.thumbnailImagePath("beginThumbnailImagePath")
		.mainImagePath("beginMainImagePath")
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
		.thumbnailImagePath("middleThumbnailImagePath")
		.mainImagePath("middleMainImagePath")
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
		.thumbnailImagePath("endThumbnailImagePath")
		.mainImagePath("endMainImagePath")
		.build();
}
