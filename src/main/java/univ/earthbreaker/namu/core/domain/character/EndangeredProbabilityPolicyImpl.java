package univ.earthbreaker.namu.core.domain.character;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class EndangeredProbabilityPolicyImpl implements EndangeredProbabilityPolicy {

	private static final int IS_ENDANGERED_PROBABILITY = 20;
	private static final int MAX_BOUND = 100;
	private static final Random RANDOM = new Random();

	@Override
	public boolean determineEndangered() {
		return RANDOM.nextInt(MAX_BOUND) <= IS_ENDANGERED_PROBABILITY;
	}
}
