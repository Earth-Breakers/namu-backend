package univ.earthbreaker.namu.app.kafka.consumer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class MissionRetryerFactory {

	private final Map<RetryStep, MissionRetryer> retryers;

	public MissionRetryerFactory(List<MissionRetryer> retryers) {
		this.retryers = retryers.stream()
			.collect(Collectors.toMap(MissionRetryer::supportStep, retryer -> retryer));
	}

	public MissionRetryer get(RetryStep retryStep) {
		return retryers.get(retryStep);
	}
}
