package univ.earthbreaker.namu.core.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RefreshTokenTest {

	@DisplayName("refreshToken 의 만료 시간과 현재 시간을 비교해, 만료된 토큰인지의 여부를 반환한다")
	@ParameterizedTest
	@MethodSource("provideCurrent")
	void isExpired(LocalDateTime current, boolean expect) {
		// given
		LocalDateTime expiresIn = LocalDateTime.of(2017, 1, 1, 0, 0);
		RefreshToken refreshToken = RefreshToken.create("refreshToken", expiresIn, 1L);

		// when
		boolean actual = refreshToken.isExpired(current);

		// then
		assertThat(actual).isEqualTo(expect);
	}

	private static @NotNull Stream<Arguments> provideCurrent() {
		return Stream.of(
			Arguments.of(LocalDateTime.of(2016, 12, 31, 23, 59), false),
			Arguments.of(LocalDateTime.of(2017, 1, 1, 0, 1), true)
		);
	}
}
