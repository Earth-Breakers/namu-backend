package univ.earthbreaker.namu.core.domain.post;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class RelatedPostRetrieveQueryTest {

	private static final long MEMBER_NO = 1L;
	private static final long MISSION_NO = 1L;
	private static final int PAGE = 0;
	private static final int SIZE = 4;

	@DisplayName("size 의 값이 4 이상 10 이하라면 아무런 예외도 발생시키지 않는다")
	@ParameterizedTest
	@ValueSource(ints = {4, 10})
	void success_sizeValidate(int size) {
		// when, then
		assertDoesNotThrow(() -> RelatedPostRetrieveQuery.of(MEMBER_NO, MISSION_NO, PAGE, size, null));
	}

	@DisplayName("size 의 값이 4 보다 작거나, 10보다 크거나, null 인 경우 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideSize")
	void fail_sizeValidate(Integer size) {
		// when, then
		assertThatThrownBy(() -> RelatedPostRetrieveQuery.of(MEMBER_NO, MISSION_NO, PAGE, size, null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("size 는 최소 4, 최대 10 까지 가능합니다. 또한, null 이 될 수 없습니다");
	}

	@DisplayName("sort key 형식이 {정렬 필드}#{정렬 순서} 이거나, null, 또는 공백이라면 아무 예외도 발생시키지 않는다")
	@ParameterizedTest
	@MethodSource("provideSuccessSortKey")
	void success_sortKeyValidate(String sortKey) {
		// when, then
		assertDoesNotThrow(() -> RelatedPostRetrieveQuery.of(MEMBER_NO, MISSION_NO, PAGE, SIZE, sortKey));
	}

	@DisplayName("sort key 형식이 '{정렬 필드}#{정렬 순서}' 가 아니라면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideFailSortKey")
	void fail_sortKeyValidate(String sortKey) {
		// when, then
		assertThatThrownBy(() -> RelatedPostRetrieveQuery.of(MEMBER_NO, MISSION_NO, PAGE, SIZE, sortKey))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("정렬 요청 형식은 다음과 같아야 합니다 : {정렬 필드}#{정렬 순서}");
	}

	@DisplayName("memberNo, relatedMissionNo, page 중 하나라도 공백이 들어가면 예외를 발생시킨다")
	@ParameterizedTest
	@MethodSource("provideMemberNoAndMissionNoAndPage")
	void fail_validate(Long memberNo, Long missionNo, Integer page) {
		// when, then
		assertThatThrownBy(() -> RelatedPostRetrieveQuery.of(memberNo, missionNo, page, SIZE, null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("memberNo, relatedMissionNo, page 는 null 이 될 수 없습니다");
	}

	private static @NotNull Stream<Integer> provideSize() {
		return Stream.of(3, 11, null);
	}

	private static @NotNull Stream<String> provideSuccessSortKey() {
		return Stream.of("", null, "id#DESC", "id#ASC", "id#desc", "id#asc");
	}

	private static @NotNull Stream<String> provideFailSortKey() {
		return Stream.of("id,DESC", "asc#id");
	}

	private static @NotNull Stream<Arguments> provideMemberNoAndMissionNoAndPage() {
		return Stream.of(
			Arguments.of(null, 1L, 0),
			Arguments.of(1L, null, 0),
			Arguments.of(1L, 1L, null),
			Arguments.of(null, null, 0),
			Arguments.of(1L, null, null),
			Arguments.of(1L, null, null),
			Arguments.of(null, null, null)
		);
	}
}
