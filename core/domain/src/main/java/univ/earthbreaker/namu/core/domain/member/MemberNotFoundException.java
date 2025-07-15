package univ.earthbreaker.namu.core.domain.member;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

	private MemberNotFoundException(String domainName) {
		super(domainName);
	}

	public static @NotNull MemberNotFoundException notFound(long memberNo) {
		return new MemberNotFoundException(String.format("회원 %s", memberNo));
	}
}
