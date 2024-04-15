package univ.earthbreaker.namu.core.domain.point;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.ENERGY;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.FRIEND_ENERGY;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.FRIEND_NO;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.USE_POINT_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EnergyPointManagerTest {

	private @Mock EnergyPointFinder energyPointFinder;
	private @Mock EnergyPointRepository energyPointRepository;
	private @InjectMocks EnergyPointManager energyPointManager;

	@DisplayName("회원의 현재 에너지 포인트를 찾아와, 사용할 에너지 만큼 사용하고, 사용한 뒤 남은 값으로 갱신한다")
	@Test
	void useEnergyPoint() {
	    // given
		when(energyPointFinder.find(MEMBER_NO))
			.thenReturn(ENERGY);

	    // when
		energyPointManager.useEnergyPoint(MEMBER_NO, USE_POINT_VALUE);

	    // then
		verify(energyPointRepository).updatePoint(Mockito.any(PointUpdateDbCommand.class));
	}

	@DisplayName("현재 회원의 에너지에서 선물할 에너지 만큼 감소시키고, 선물 받을 회원의 에너지에 선물할 에너지 만큼 증가시킨다")
	@Test
	void transfer() {
	    // given
		when(energyPointFinder.find(MEMBER_NO))
			.thenReturn(ENERGY);
		when(energyPointFinder.find(FRIEND_NO))
			.thenReturn(FRIEND_ENERGY);

	    // when
		energyPointManager.transfer(MEMBER_NO, FRIEND_NO, USE_POINT_VALUE);

	    // then
		verify(energyPointRepository, times(2)).updatePoint(Mockito.any(PointUpdateDbCommand.class));
	}
}
