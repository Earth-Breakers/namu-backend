package univ.earthbreaker.namu.core.domain.character;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class Exp {

	private static final int INITIAL_EXP = 0;

	private final int requiredExp;
	private final int currentExp;
	private final boolean isFull;

	private Exp(int requiredExp, int currentExp, boolean isFull) {
		this.requiredExp = requiredExp;
		this.currentExp = currentExp;
		this.isFull = isFull;
	}

	Exp calculate(int givenExp) {
		int addAfter = currentExp + givenExp;
		int remainExp = requiredExp - addAfter;

		if (remainExp < INITIAL_EXP) {
			throw CurrentCharacterBadRequestException.expOverflow();
		} else if (remainExp == INITIAL_EXP) {
			return new Exp(requiredExp, addAfter, true);
		} else {
			return new Exp(requiredExp, addAfter, false);
		}
	}

	static @NotNull Exp of(int requiredExp, int currentExp) {
		if ((requiredExp - currentExp) == INITIAL_EXP) {
			return new Exp(requiredExp, currentExp, true);
		}
		return new Exp(requiredExp, currentExp, false);
	}

	static @NotNull Exp initialize(int requiredExp) {
		return new Exp(requiredExp, INITIAL_EXP, false);
	}

	int getRequiredExp() {
		return requiredExp;
	}

	int getCurrentExp() {
		return currentExp;
	}

	boolean isFull() {
		return isFull;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Exp exp = (Exp)o;
		return requiredExp == exp.requiredExp && currentExp == exp.currentExp && isFull == exp.isFull;
	}

	@Override
	public int hashCode() {
		return Objects.hash(requiredExp, currentExp, isFull);
	}
}
