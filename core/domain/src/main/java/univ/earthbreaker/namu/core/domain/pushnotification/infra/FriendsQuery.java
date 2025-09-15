package univ.earthbreaker.namu.core.domain.pushnotification.infra;

import java.util.List;

public record FriendsQuery(
	List<Long> memberNos
) {
}
