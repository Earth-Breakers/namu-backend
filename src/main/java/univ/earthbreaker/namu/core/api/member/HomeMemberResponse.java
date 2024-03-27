package univ.earthbreaker.namu.core.api.member;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.member.Member;

public record HomeMemberResponse(
	long memberNo,
	int level,
	int requiredExp,
	int currentExp
) {
	static @NotNull HomeMemberResponse from(@NotNull Member member) {
		return new HomeMemberResponse(
			member.getNo(),
			member.getLevel(),
			member.getRequiredExp(),
			member.getCurrentExp()
		);
	}
}
