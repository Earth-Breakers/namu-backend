package univ.earthbreaker.namu.external.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FirebaseBackOffTest {

	private static final FirebaseBackOff FIREBASE_BACK_OFF_1 = new FirebaseBackOff();
	private static final FirebaseBackOff FIREBASE_BACK_OFF_2 = new FirebaseBackOff();
	private static final FirebaseBackOff FIREBASE_BACK_OFF_3 = new FirebaseBackOff();

	@DisplayName("다음 지수 백오프 대기 시간을 계산해 반환한다")
	@Test
	void nextBackOffMillis() {
		// when
		processEachFirebaseBackOff();

		// then
		assertAll(
			() -> assertThat(FIREBASE_BACK_OFF_1.getCurrentInterval()).isEqualTo(1000),
			() -> assertThat(FIREBASE_BACK_OFF_2.getCurrentInterval()).isEqualTo(2000),
			() -> assertThat(FIREBASE_BACK_OFF_3.getCurrentInterval()).isEqualTo(4000)
		);
		resetEachFirebaseBackOff();
	}

	@DisplayName("지수 백오프의 대기 시간과 재시도 횟수를 초기화한다")
	@Test
	void reset() {
		// given
		FirebaseBackOff firebaseBackOff = new FirebaseBackOff();

		// when
		firebaseBackOff.reset();

		// then
		assertThat(firebaseBackOff.getCurrentInterval()).isEqualTo(1000);
		assertThat(firebaseBackOff.getCurrentRetryCount()).isZero();
	}

	@DisplayName("지수 백오프의 재시도 횟수가 3이 되면 true 를 반환하고, 0 이상 2 이하이면 false 를 반환한다")
	@ParameterizedTest
	@MethodSource("provideFirebaseBackOffAndStopExpect")
	void isStopped(@NotNull FirebaseBackOff firebaseBackOff, boolean expect) {
		// when
		boolean actual = firebaseBackOff.isStopped();

		// then
		assertThat(actual).isEqualTo(expect);
	}

	private static @NotNull Stream<Arguments> provideFirebaseBackOffAndStopExpect() {
		processEachFirebaseBackOff();
		return Stream.of(
			Arguments.of(FIREBASE_BACK_OFF_1, false),
			Arguments.of(FIREBASE_BACK_OFF_2, false),
			Arguments.of(FIREBASE_BACK_OFF_3, true)
		);
	}

	private static void processEachFirebaseBackOff() {
		FIREBASE_BACK_OFF_1.nextBackOffMillis();

		FIREBASE_BACK_OFF_2.nextBackOffMillis();
		FIREBASE_BACK_OFF_2.nextBackOffMillis();

		FIREBASE_BACK_OFF_3.nextBackOffMillis();
		FIREBASE_BACK_OFF_3.nextBackOffMillis();
		FIREBASE_BACK_OFF_3.nextBackOffMillis();
	}

	private void resetEachFirebaseBackOff() {
		FIREBASE_BACK_OFF_1.reset();
		FIREBASE_BACK_OFF_2.reset();
		FIREBASE_BACK_OFF_3.reset();
	}
}
