package univ.earthbreaker.namu.core.domain.reaction;

public class ReactionFixture {

	public static final long MEMBER_NO = 1L;
	public static final long TARGET_NO = 1L;
	public static final String TARGET_TYPE_POST = "POST";
	public static final String REACTION_TYPE = "LIKE";

	public static final ReactionCommand REACTION_COMMAND = new ReactionCommand(MEMBER_NO, TARGET_NO, TARGET_TYPE_POST, REACTION_TYPE);
}
