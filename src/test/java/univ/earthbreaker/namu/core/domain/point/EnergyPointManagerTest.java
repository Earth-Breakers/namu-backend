package univ.earthbreaker.namu.core.domain.point;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.point.EnergyPointFixture.ENERGY;
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
		verify(energyPointRepository).update(Mockito.any(PointUpdateDbCommand.class));
	}
}
