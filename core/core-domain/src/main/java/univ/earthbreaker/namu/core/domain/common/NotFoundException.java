package univ.earthbreaker.namu.core.domain.common;

public abstract class NotFoundException extends RuntimeException {

	private static final String NOT_FOUND_MESSAGE_FORMAT = "존재하지 않는 %s 입니다";

	protected NotFoundException(String domainName) {
		super(String.format(NOT_FOUND_MESSAGE_FORMAT, domainName));
	}
}
