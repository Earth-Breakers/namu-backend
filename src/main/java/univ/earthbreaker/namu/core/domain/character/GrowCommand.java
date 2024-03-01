package univ.earthbreaker.namu.core.domain.character;

import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class GrowCommand extends SelfValidating<GrowCommand> {

	private final @NotNull Level level;
	private final @NotNull Integer groupNumber;
	private final @NotNull CharacterType characterType;

	public GrowCommand(Level level, int groupNumber, CharacterType characterType) {
		this.level = level;
		this.groupNumber = groupNumber;
		this.characterType = characterType;
		this.validateSelf("level, groupNumber 와 characterType 은 null 값이 될 수 없습니다");
	}
}
