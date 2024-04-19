package univ.earthbreaker.namu.core.domain.post;

import java.time.LocalDate;

public record PostDbQuery(
	long memberNo,
	LocalDate date
) {
}
