package univ.earthbreaker.namu.core.domain.energy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.exception.LockAcquisitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.CannotAcquireLockException;

import univ.earthbreaker.namu.database.core.energy.EnergyPointJpaEntity;
import univ.earthbreaker.namu.database.core.energy.EnergyPointJpaRepository;

@SpringBootTest
class EnergyGiftServiceTest {

	@Autowired
	private EnergyPointJpaRepository energyPointJpaRepository;

	@Autowired
	private EnergyGiftService giftService;

	@DisplayName("")
	@Test
	void test() throws InterruptedException {
		// given
		energyPointJpaRepository.saveAll(
			List.of(
				new EnergyPointJpaEntity(180, 1998L),
				new EnergyPointJpaEntity(200, 1999L),
				new EnergyPointJpaEntity(150, 2000L)
			)
		);
		int threadCount = 2; // 두 개의 스레드를 사용하여 테스트
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		// 동시에 트랜잭션을 실행
		System.out.println("==============================");
		for (int i = 0; i < threadCount; i++) {
			int memberNo = (i == 0) ? 1998 : 2000; // 스레드마다 다른 회원 번호 사용
			int targetMemberNo = (i == 0) ? 2000 : 1998; // 스레드마다 다른 대상 회원 번호 사용

			executorService.execute(() -> {
				try {
					giftService.givePointToFriend(new EnergyGiftCommand(memberNo, targetMemberNo, 100));
				}
				// catch (CannotAcquireLockException e) {
				// 	System.out.println("========데드락 감지========");
				// 	System.out.println(e.getMessage());
				// }
				finally {
					latch.countDown();
				}
			});
		}
		System.out.println("==============================");

		// then
		latch.await();
		EnergyPointJpaEntity member = energyPointJpaRepository.findByMemberNo(1998L);
		EnergyPointJpaEntity target = energyPointJpaRepository.findByMemberNo(2000L);
		System.out.println(member.toString());
		System.out.println(target.toString());
	}
}
