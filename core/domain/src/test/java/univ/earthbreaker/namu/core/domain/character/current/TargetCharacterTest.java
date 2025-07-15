package univ.earthbreaker.namu.core.domain.character.current;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import univ.earthbreaker.namu.core.domain.character.NamuCharacter;

class TargetCharacterTest {

	@DisplayName("캐릭터(NamuCharacter)를 받아 객체를 생성할 수 있다")
	@Test
	void changeTo() {
		// given
		final long targetCharacterNo = Long.MAX_VALUE;
		final int targetCharacterGroupNumber = Integer.MAX_VALUE;
		final String targetCharacterImagePath = "namuImagePath";
		NamuCharacter namuCharacter = NamuCharacter.builder()
			.no(targetCharacterNo)
			.groupNumber(targetCharacterGroupNumber)
			.mainImagePath(targetCharacterImagePath)
			.build();

		// when
		TargetCharacter targetCharacter = TargetCharacter.changeTo(namuCharacter);

		// then
		assertAll(
			() -> assertThat(targetCharacter.getCharacterNo()).isEqualTo(targetCharacterNo),
			() -> assertThat(targetCharacter.getGroupNumber()).isEqualTo(targetCharacterGroupNumber),
			() -> assertThat(targetCharacter.getMainImagePath()).isEqualTo(targetCharacterImagePath)
		);
	}
}
