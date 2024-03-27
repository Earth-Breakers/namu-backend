package univ.earthbreaker.namu.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherAdapter implements EventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(Object event) {
		applicationEventPublisher.publishEvent(event);
	}
}
