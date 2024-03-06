package univ.earthbreaker.namu.core.domain.character;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class CharacterStatus {

	private final Level level;
	private final Exp exp;
	private final boolean canLevelUp;

	private CharacterStatus(Level level, Exp exp, boolean canLevelUp) {
		this.level = level;
		this.exp = exp;
		this.canLevelUp = canLevelUp;
	}

	CharacterStatus addExp(int givenExp) {
		Exp calculateAfterExp = exp.calculate(givenExp);
		if (checkCanLevelUp(calculateAfterExp, level)) {
			return new CharacterStatus(level, calculateAfterExp, true);
		}
		return new CharacterStatus(level, calculateAfterExp, false);
	}

	int calculateToNextLevelValue() {
		return level.up();
	}

	boolean isExpectLevelOverFlow(int expectedNextLevelValue) {
		return level.isOverflow(expectedNextLevelValue);
	}

	boolean isLevelMiddle() {
		return level.isMiddle();
	}

	boolean isLevelBegin() {
		return level.isBegin();
	}

	static @NotNull CharacterStatus of(int levelValue, int requiredExp, int currentExp) {
		Level level = Level.of(levelValue);
		Exp exp = Exp.of(requiredExp, currentExp);
		if (checkCanLevelUp(exp, level)) {
			return new CharacterStatus(level, exp, true);
		}
		return new CharacterStatus(level, exp, false);
	}

	private static boolean checkCanLevelUp(@NotNull Exp calculagteAfterExp, @NotNull Level level) {
		return calculagteAfterExp.isFull() && level.isGrowable();
	}

	static @NotNull CharacterStatus initialize(int requiredExp) {
		return new CharacterStatus(Level.BEGIN, Exp.initialize(requiredExp), false);
	}

	static @NotNull CharacterStatus createNext(int level, int requiredExp) {
		return new CharacterStatus(Level.of(level), Exp.initialize(requiredExp), false);
	}

	int getLevelValue() {
		return level.getValue();
	}

	int getRequiredExp() {
		return exp.getRequiredExp();
	}

	int getCurrentExp() {
		return exp.getCurrentExp();
	}

	boolean isCanLevelUp() {
		return canLevelUp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CharacterStatus that = (CharacterStatus)o;
		return canLevelUp == that.canLevelUp && level == that.level && exp.equals(that.exp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(level, exp, canLevelUp);
	}
}
