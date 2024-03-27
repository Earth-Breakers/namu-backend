package univ.earthbreaker.namu.external.aws.image;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component
public class AwsS3ImageManager implements ImageManager {

	private final AmazonS3 amazonS3;
	private final String s3BucketName;
	private final ImagePathKeyGenerator imagePathKeyGenerator;

	public AwsS3ImageManager(
		AmazonS3 amazonS3,
		@Value("${cloud.aws.s3.bucket-name}") String s3BucketName,
		ImagePathKeyGenerator imagePathKeyGenerator
	) {
		this.amazonS3 = amazonS3;
		this.s3BucketName = s3BucketName;
		this.imagePathKeyGenerator = imagePathKeyGenerator;
	}

	@Override
	public @NotNull String upload(@NotNull ImageUploadCommand command) {
		MultipartFile imageFile = command.imageFile();

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(imageFile.getContentType());
		objectMetadata.setContentLength(imageFile.getSize());

		String imagePathKey = imagePathKeyGenerator.generate(command.memberNo(), imageFile.getOriginalFilename());
		try {
			amazonS3.putObject(s3BucketName, imagePathKey, imageFile.getInputStream(), objectMetadata);
		} catch (AmazonClientException | IOException e) {
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
