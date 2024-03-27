package univ.earthbreaker.namu.core.domain.character;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class NamuCharacter {

	private final long no;
	private final CharacterType type;
	private final Gender gender;
	private final boolean isEndangered;
	private final int groupNumber;
	private final int level;
	private final int requiredExp;
	private final String name;
	private final String description;
	private final String thumbnailImagePath;
	private final String mainImagePath;

	public NamuCharacter(
		long no,
		CharacterType type,
		Gender gender,
		boolean isEndangered,
		int groupNumber,
		int level,
		int requiredExp,
		String name,
		String description,
		String thumbnailImagePath,
		String mainImagePath
	) {
		this.no = no;
		this.type = type;
		this.gender = gender;
		this.isEndangered = isEndangered;
		this.groupNumber = groupNumber;
		this.level = level;
		this.requiredExp = requiredExp;
		this.name = name;
		this.description = description;
		this.thumbnailImagePath = thumbnailImagePath;
		this.mainImagePath = mainImagePath;
	}

	public static @NotNull NamuCharacterBuilder builder() {
		return new NamuCharacterBuilder();
	}

	public static class NamuCharacterBuilder {
		private long no;
		private CharacterType type;
		private Gender gender;
		private boolean isEndangered;
		private int groupNumber;
		private int level;
		private int requiredExp;
		private String name;
		private String description;
		private String thumbnailImagePath;
		private String mainImagePath;

		public NamuCharacterBuilder no(long no) {
			this.no = no;
			return this;
		}

		public NamuCharacterBuilder type(CharacterType type) {
			this.type = type;
			return this;
		}

		public NamuCharacterBuilder gender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public NamuCharacterBuilder isEndangered(boolean isEndangered) {
			this.isEndangered = isEndangered;
			return this;
		}

		public NamuCharacterBuilder groupNumber(int groupNumber) {
			this.groupNumber = groupNumber;
			return this;
		}

		public NamuCharacterBuilder level(int level) {
			this.level = level;
			return this;
		}

		public NamuCharacterBuilder requiredExp(int requiredExp) {
			this.requiredExp = requiredExp;
			return this;
		}

		public NamuCharacterBuilder name(String name) {
			this.name = name;
			return this;
		}

		public NamuCharacterBuilder description(String description) {
			this.description = description;
			return this;
		}

		public NamuCharacterBuilder thumbnailImagePath(String thumbnailImagePath) {
			this.thumbnailImagePath = thumbnailImagePath;
			return this;
		}

		public NamuCharacterBuilder mainImagePath(String mainImagePath) {
			this.mainImagePath = mainImagePath;
			return this;
		}

		public NamuCharacter build() {
			return new NamuCharacter(
				this.no,
				this.type,
				this.gender,
				this.isEndangered,
				this.groupNumber,
				this.level,
				this.requiredExp,
				this.name,
				this.description,
				this.thumbnailImagePath,
				this.mainImagePath
			);
		}
	}

	public long getNo() {
		return no;
	}

	public CharacterType getType() {
		return type;
	}

	public int getGroupNumber() {
		return groupNumber;
	}

	public String getName() {
		return name;
	}

	public int getLevelValue() {
		return level;
	}

	public int getRequiredExp() {
		return requiredExp;
	}

	public String getMainImagePath() {
		return mainImagePath;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		NamuCharacter that = (NamuCharacter)o;
		return no == that.no && isEndangered == that.isEndangered && groupNumber == that.groupNumber
			&& level == that.level
			&& requiredExp == that.requiredExp && type == that.type && gender == that.gender && name.equals(that.name)
			&& description.equals(that.description) && thumbnailImagePath.equals(that.thumbnailImagePath)
			&& mainImagePath.equals(that.mainImagePath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(no, type, gender, isEndangered, groupNumber, level, requiredExp, name, description,
			thumbnailImagePath, mainImagePath);
	}
}
