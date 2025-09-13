package univ.earthbreaker.namu.core.domain.point;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.point.infra.EnergyPointRepository;
import univ.earthbreaker.namu.core.domain.point.infra.PointUpdateDbCommand;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.point.InitEnergyPointEvent;

@ExtendWith(MockitoExtension.class)
class EnergyPointEventHandlerTest {

	private @Mock EnergyPointRepository energyPointRepository;
	private @InjectMocks EnergyPointEventHandler energyPointEventHandler;

	@DisplayName("회원이 성공한 미션에 대한 보상 포인트 지급 이벤트를 구독하고, 이벤트를 받아 회원의 포인트를 추가해 갱신한다")
	@Test
	void giveRewardPoint() {
		// given
		AddRewardPointEvent event = new AddRewardPointEvent(1L, 3);

		// when
		energyPointEventHandler.giveRewardPoint(event);

		// then
		verify(energyPointRepository).receivePoint(new PointUpdateDbCommand(event.memberNo(), event.point()));
	}

	@DisplayName("회원 계정 생성 시 초기 에너지 포인트 지급 이벤트를 구독하고, 이벤트를 받아 초기 에너지 포인트를 등록한다")
	@Test
	void registerInitEnergyPoint() {
	    // given
		InitEnergyPointEvent event = new InitEnergyPointEvent(1L);

		// when
		energyPointEventHandler.registerInitEnergyPoint(event);

	    // then
		verify(energyPointRepository).register(event.memberNo());
	}
}
