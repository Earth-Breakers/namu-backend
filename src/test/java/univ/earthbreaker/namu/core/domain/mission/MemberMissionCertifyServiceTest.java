package univ.earthbreaker.namu.core.domain.mission;

import static org.junit.jupiter.api.Assertions.assertAll;
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

import univ.earthbreaker.namu.event.EventPublisher;
import univ.earthbreaker.namu.event.image.DeleteUploadedImageEvent;
import univ.earthbreaker.namu.event.point.AddRewardPointEvent;
import univ.earthbreaker.namu.event.post.PostCreateEvent;

@ExtendWith(MockitoExtension.class)
class MemberMissionCertifyServiceTest {

	private static final String MISSION_POST_CONTENT = "content";
	private static final String MISSION_POST_IMAGE_PATH_KEY = "imagePathKey";

	private @Mock MemberMissionFinder memberMissionFinder;
	private @Mock MissionCertifyHandler missionCertifyHandler;
	private @Mock EventPublisher eventPublisher;
	private @InjectMocks MemberMissionCertifyService memberMissionCertifyService;

	@DisplayName("""
		회원의 인증 성공한 미션을 찾아와 '미션 성공'상태로 변경하고,
		보상 포인트 지급 이벤트와 게시글 업로드 이벤트, 롤백 발생 시 업로드한 이미지 삭제 이벤트를 발행한다""")
	@Test
	void successMission() {
		// given
		when(memberMissionFinder.find(MEMBER_NO, DEFAULT_MISSION_NO))
			.thenReturn(DEFAULT_MISSION_READY);
		MemberMission successMission = DEFAULT_MISSION_READY.success();
		when(missionCertifyHandler.success(DEFAULT_MISSION_READY))
			.thenReturn(successMission);

		// when
		memberMissionCertifyService.successMission(
			new MissionCompleteCommand(MEMBER_NO, DEFAULT_MISSION_NO),
			new CertifiedMissionPostCommand(MEMBER_NO, MISSION_POST_CONTENT, MISSION_POST_IMAGE_PATH_KEY)
		);

		// then
		assertAll(
			() -> verify(eventPublisher)
				.publish(new AddRewardPointEvent(successMission.getMemberNo(), successMission.getRewardPoint())),
			() -> verify(eventPublisher)
				.publish(new PostCreateEvent(
					MEMBER_NO, successMission.getTitle(), MISSION_POST_CONTENT,
					MISSION_POST_IMAGE_PATH_KEY, successMission.getNo()
				)),
			() -> verify(eventPublisher).publish(new DeleteUploadedImageEvent(MISSION_POST_IMAGE_PATH_KEY))
		);
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
