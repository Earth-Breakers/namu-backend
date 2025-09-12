package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component
public class AwsS3ImageManager implements ImageManager {

	private final AmazonS3 amazonS3;
	private final String s3BucketName;

	public AwsS3ImageManager(
		AmazonS3 amazonS3,
		@Value("${cloud.aws.s3.bucket-name}") String s3BucketName
	) {
		this.amazonS3 = amazonS3;
		this.s3BucketName = s3BucketName;
	}

	@Override
	public @NotNull String upload(@NotNull ImageUploadCommand command) {
		ImagePathKeyGenerator imagePathKeyGenerator = command.imagePathKeyGenerator();

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(command.contentType());
		objectMetadata.setContentLength(command.contentLength());

		String imagePathKey = imagePathKeyGenerator.generate(command.memberKey(), command.originalFilename());
		try {
			amazonS3.putObject(s3BucketName, imagePathKey, command.inputStream(), objectMetadata);
		} catch (AmazonClientException e) {
			throw ImageProcessException.uploadFail(e.getMessage(), imagePathKey);
		}
		return imagePathKey;
	}

	@Override
	public void delete(String imagePathKey) {
		try {
			amazonS3.deleteObject(s3BucketName, imagePathKey);
		} catch (AmazonClientException e) {
			throw ImageProcessException.deleteFail(e.getMessage(), imagePathKey);
		}
	}
}
