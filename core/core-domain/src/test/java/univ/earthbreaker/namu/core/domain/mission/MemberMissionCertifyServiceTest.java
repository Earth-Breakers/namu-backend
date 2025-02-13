package univ.earthbreaker.namu.core.domain.mission;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.DEFAULT_MISSION_NO;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.DEFAULT_MISSION_READY;
import static univ.earthbreaker.namu.core.domain.mission.MissionFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.image.DeleteExternalUploadedImageEvent;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@ExtendWith(MockitoExtension.class)
class MemberMissionCertifyServiceTest {

	private static final String MISSION_POST_CONTENT = "content";
	private static final String MISSION_POST_IMAGE_PATH_KEY = "imagePathKey";

	private @Mock MemberMissionFinder memberMissionFinder;
	private @Mock MissionCertifyHandler missionCertifyHandler;
	private @Mock EventPublisher eventPublisher;
	private @Mock TransactionTemplate transactionTemplate;
	private @InjectMocks MemberMissionCertifyService memberMissionCertifyService;

	@DisplayName("회원의 인증 성공한 미션을 찾아와 '미션 성공'상태로 변경하고, 보상 포인트 지급 이벤트와 게시글 업로드 이벤트를 발행한다")
	@Test
	void successMission_and_publishEventInTransaction() {
		// given
		when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class)))
			.thenAnswer(invocation -> {
				TransactionCallbackWithoutResult callback = invocation.getArgument(0);
				TransactionStatus status = mock(TransactionStatus.class);

				when(memberMissionFinder.find(MEMBER_NO, DEFAULT_MISSION_NO))
					.thenReturn(DEFAULT_MISSION_READY);
				when(missionCertifyHandler.success(DEFAULT_MISSION_READY))
					.thenReturn(DEFAULT_MISSION_READY.success());

				callback.doInTransaction(status);

				return null;
			});

		// when
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(MEMBER_NO, DEFAULT_MISSION_NO),
			new CertifiedMissionPostCommand(MEMBER_NO, MISSION_POST_CONTENT, MISSION_POST_IMAGE_PATH_KEY)
		);

		// then
		MemberMission successMission = DEFAULT_MISSION_READY.success();
		assertAll(
			() -> verify(eventPublisher)
				.publish(new AddRewardPointEvent(successMission.getMemberNo(), successMission.getRewardPoint())),
			() -> verify(eventPublisher)
				.publish(new PostCreateEvent(
					MEMBER_NO, successMission.getActivity(), MISSION_POST_CONTENT,
					MISSION_POST_IMAGE_PATH_KEY, successMission.getNo()
				))
		);
	}

	@DisplayName("트랜잭션 내에서 예외가 발생하면 트랜잭션을 롤백하고 이미지 삭제 이벤트를 발행한다")
	@Test
	void rollbackTransactionAndTriggerImageDeletionEventOnException() {
	    // given
		when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class)))
			.thenAnswer(invocation -> {
				TransactionCallbackWithoutResult callback = invocation.getArgument(0);
				TransactionStatus status = mock(TransactionStatus.class);

				doThrow(new RuntimeException("테스트 런타임 예외 발생!"))
					.when(missionCertifyHandler).success(any(MemberMission.class));
				doAnswer(invocationOnMock -> {
					when(status.isRollbackOnly()).thenReturn(true);
					return null;
				}).when(status).setRollbackOnly();

				callback.doInTransaction(status);

				return null;
			});

	    // when
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(MEMBER_NO, DEFAULT_MISSION_NO),
			new CertifiedMissionPostCommand(MEMBER_NO, MISSION_POST_CONTENT, MISSION_POST_IMAGE_PATH_KEY)
		);

	    // then
		verify(eventPublisher).publish(new DeleteExternalUploadedImageEvent(MISSION_POST_IMAGE_PATH_KEY));
	}

	@DisplayName("회원의 인증 성공한 미션을 찾아와 '미션 실패'상태로 변경한다")
	@Test
	void failureMission() {
	    // given
		when(memberMissionFinder.find(MEMBER_NO, DEFAULT_MISSION_NO))
			.thenReturn(DEFAULT_MISSION_READY);

	    // when
		memberMissionCertifyService.failureMission(new MissionCompleteCommand(MEMBER_NO, DEFAULT_MISSION_NO));

		// then
		verify(missionCertifyHandler).failure(DEFAULT_MISSION_READY);
	}
}
