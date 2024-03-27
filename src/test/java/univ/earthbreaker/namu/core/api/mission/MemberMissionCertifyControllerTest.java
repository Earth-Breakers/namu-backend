package univ.earthbreaker.namu.core.api.mission;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.API_DOCUMENT_IDENTIFIER;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationRequestPreprocessor;
import static univ.earthbreaker.namu.support.apidocs.ApiDocsUtils.operationResponsePreprocessor;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.ResultActions;

import univ.earthbreaker.namu.core.api.PresentationTest;
import univ.earthbreaker.namu.core.domain.mission.MemberMissionCertifyService;
import univ.earthbreaker.namu.external.aws.image.ImageManager;
import univ.earthbreaker.namu.external.aws.image.ImageUploadCommand;

class MemberMissionCertifyControllerTest extends PresentationTest {

	private static final String SUCCESS_MISSION_POST_URI = "/v1/missions/certification/success/{missionNo}";
	private static final String FAIL_MISSION_URI = "/v1/missions/certification/fail/{missionNo}";

	private static final Long MISSION_NO = 1L;
	private static final String PART_REQUEST_NAME = "content";
	private static final String MULTIPART_FILE_REQUEST_NAME = "imageFile";

	private final MemberMissionCertifyService memberMissionCertifyService = Mockito.mock(MemberMissionCertifyService.class);
	private final ImageManager imageManager = Mockito.mock(ImageManager.class);
	private final MemberMissionCertifyController memberMissionCertifyController
		= new MemberMissionCertifyController(memberMissionCertifyService, imageManager);

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = mockControllerWithAuthorization(memberMissionCertifyController);
	}

	@DisplayName("""
		미션 인증에 성공하면
		미션 상태를 '완료'로 변경, 해당 미션에 맞는 포인트를 지급한 뒤
		미션 인증에 성공한 이미지와, 함께 작성한 글을 받아 게시글을 생성한 이후
		status 201을 반환한다""")
	@Test
	void success() throws Exception {
		// given
		Mockito.when(imageManager.upload(Mockito.any(ImageUploadCommand.class)))
			.thenReturn("imagePathKey");

		MockPart contentPart = new MockPart(
			PART_REQUEST_NAME,
			"미션 인증에 성공한 이미지와 함께 작성한 글".getBytes(StandardCharsets.UTF_8)
		);
		MockMultipartFile successMissionImageFile = new MockMultipartFile(
			MULTIPART_FILE_REQUEST_NAME,
			"originFileName",
			"text/plain",
			"미션 인증에 성공한 이미지".getBytes(StandardCharsets.UTF_8)
		);

		// when
		ResultActions resultActions = whenPostMultipartWithAuthorization(
			SUCCESS_MISSION_POST_URI,
			MISSION_NO,
			contentPart,
			successMissionImageFile
		);

		// then
		resultActions.andExpect(status().isCreated());

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				pathParameters(
					parameterWithName("missionNo").description("미션 인증에 성공한 미션 번호")
				),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
				),
				requestParts(
					partWithName(PART_REQUEST_NAME).description("미션 인증에 성공한 이미지와 함께 작성한 글"),
					partWithName(MULTIPART_FILE_REQUEST_NAME).description("미션 인증에 성공한 이미지 (Multipart Form Data)")
				)
			)
		);
	}

	@DisplayName("미션 인증에 실패하면, 미션 상태를 '실패'로 변경하고 status 200 을 반환한다")
	@Test
	void failure() throws Exception {
	    // given

	    // when
		ResultActions resultActions = whenPatchWithAuthorization(FAIL_MISSION_URI, MISSION_NO);

		// then
		resultActions.andExpect(status().isOk());

		// apidocs
		resultActions.andDo(
			document(
				API_DOCUMENT_IDENTIFIER,
				operationRequestPreprocessor(),
				operationResponsePreprocessor(),
				pathParameters(
					parameterWithName("missionNo").description("미션 인증에 실패한 미션 번호")
				),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("회원의 access 토큰 값")
				)
			)
		);
	}
}
