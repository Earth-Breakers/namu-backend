package univ.earthbreaker.namu.external.aws.image;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.event.image.DeleteUploadedImageEvent;

@ExtendWith(MockitoExtension.class)
class AwsS3ImageEventHandlerTest {

	private @Mock ImageManager imageManager;
	private @InjectMocks AwsS3ImageEventHandler awsS3ImageEventHandler;

	@DisplayName("""
		회원이 성공한 미션의 상태 변경, 보상 포인트 지급, 게시글 업로드 중 예외가 발생해 트랜잭션이 롤백되면
		s3 에 업로드 된 이미지를 삭제하는 이벤트를 실행해, 이미지를 삭제한다""")
	@Test
	void deleteImage() {
		// given
		DeleteUploadedImageEvent event = new DeleteUploadedImageEvent("imagePathKey");

		// when
		awsS3ImageEventHandler.deleteImage(event);

		// then
		verify(imageManager).delete(event.imagePathKey());
	}
}
