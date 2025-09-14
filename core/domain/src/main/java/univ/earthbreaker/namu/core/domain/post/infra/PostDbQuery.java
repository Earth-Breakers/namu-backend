package univ.earthbreaker.namu.core.domain.post.infra;

import java.time.LocalDate;

public record PostDbQuery(
	long memberNo,
	LocalDate date
) {
}
