package univ.earthbreaker.namu.core.domain.common;

public abstract class UnAuthorizedException extends RuntimeException {

	protected UnAuthorizedException(String message) {
		super(message);
	}
}
