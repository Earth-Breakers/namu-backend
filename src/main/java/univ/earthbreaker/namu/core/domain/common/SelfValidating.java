package univ.earthbreaker.namu.core.domain.common;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class SelfValidating<T> {

	private final Validator validator;

	protected SelfValidating() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	/**
	 * ClassCastException 을 발생시킬 가능성이 있는 일반적인 캐스트와 달리,
	 * SelfValidating 클래스를 상속한 클래스들은 T 타입으로 제한되기 때문에
	 * 해당 캐스트는 안전함
	 * @param message 예외 상황 메시지
	 */
	@SuppressWarnings("unchecked")
	protected void validateSelf(String message) {
		Set<ConstraintViolation<T>> violations = validator.validate((T) this);
		if (!violations.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
}
