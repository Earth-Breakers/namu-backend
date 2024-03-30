package univ.earthbreaker.namu.core.domain.point;

import static org.mockito.Mockito.verify;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.ENERGY_TYPE;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.USE_POINT_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.character.AddEnergyPointEvent;

@ExtendWith(MockitoExtension.class)
class EnergyPointProvideServiceTest {

	private @Mock EnergyPointManager energyPointManager;
	private @Mock EventPublisher eventPublisher;
	private @InjectMocks EnergyPointProvideService energyPointProvideService;

	@DisplayName("에너지 제공 커맨드를 받아, 회원의 에너지 포인트를 사용해 회원의 현재 캐릭터에게 에너지를 제공하는 이벤트를 발행한다")
	@Test
	void provideEnergyToCharacter() {
		// given
		ProvideEnergyPointCommand command = new ProvideEnergyPointCommand(MEMBER_NO, USE_POINT_VALUE, ENERGY_TYPE);

		// when
		energyPointProvideService.provideEnergyToCharacter(command);

		// then
		verify(energyPointManager).useEnergyPoint(MEMBER_NO, USE_POINT_VALUE);
		verify(eventPublisher).publish(new AddEnergyPointEvent(
			command.getMemberNo(),
			command.getPoint(),
			command.getEnergyType()
		));
	}
}
