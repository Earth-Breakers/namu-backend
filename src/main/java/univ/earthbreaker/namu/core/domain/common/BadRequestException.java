package univ.earthbreaker.namu.core.domain.common;

public abstract class BadRequestException extends RuntimeException {

	protected BadRequestException(String message) {
		super(message);
	}
}
