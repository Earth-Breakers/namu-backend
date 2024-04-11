package univ.earthbreaker.namu.core.domain.character;

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
	private final String detailImagePath;
	private final String thumbnailImagePath;
	private final String mainImagePath;
	private final String backgroundImagePath;
	private final String scripts;

	private NamuCharacter(
		long no,
		CharacterType type,
		Gender gender,
		boolean isEndangered,
		int groupNumber,
		int level,
		int requiredExp,
		String name,
		String description,
		String detailImagePath,
		String thumbnailImagePath,
		String mainImagePath,
		String backgroundImagePath,
		String scripts
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
		this.detailImagePath = detailImagePath;
		this.thumbnailImagePath = thumbnailImagePath;
		this.mainImagePath = mainImagePath;
		this.backgroundImagePath = backgroundImagePath;
		this.scripts = scripts;
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
		private String detailImagePath;
		private String thumbnailImagePath;
		private String mainImagePath;
		private String backgroundImagePath;
		private String scripts;

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

		public NamuCharacterBuilder detailImagePath(String detailImagePath) {
			this.detailImagePath = detailImagePath;
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

		public NamuCharacterBuilder backgroundImagePath(String backgroundImagePath) {
			this.backgroundImagePath = backgroundImagePath;
			return this;
		}

		public NamuCharacterBuilder scripts(String scripts) {
			this.scripts = scripts;
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
				this.detailImagePath,
				this.thumbnailImagePath,
				this.mainImagePath,
				this.backgroundImagePath,
				this.scripts
			);
		}
	}

	public Gender getGender() {
		return gender;
	}

	public boolean isEndangered() {
		return isEndangered;
	}

	public String getDescription() {
		return description;
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

	public String getDetailImagePath() {
		return detailImagePath;
	}

	public String getMainImagePath() {
		return mainImagePath;
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public String getScripts() {
		return scripts;
	}
}
