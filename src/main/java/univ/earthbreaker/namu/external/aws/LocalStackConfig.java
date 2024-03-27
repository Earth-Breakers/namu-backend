package univ.earthbreaker.namu.external.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@Profile({"local"})
public class LocalStackConfig {

	private static final String AWS_REGION = Regions.US_EAST_1.getName();
	private static final String LOCAL_STACK_ACCESS_KEY = "access-key";
	private static final String LOCAL_STACK_SECRET_KEY = "secret-key";

	private final String bucketName;
	private final String localStackEndPoint;

	public LocalStackConfig(
		@Value("${cloud.aws.s3.localstack.bucket-name}") String bucketName,
		@Value("${cloud.aws.s3.localstack.endpoint}") String localStackEndPoint
	) {
		this.bucketName = bucketName;
		this.localStackEndPoint = localStackEndPoint;
	}

	@Bean
	public AmazonS3 amazonS3() {
		AwsClientBuilder.EndpointConfiguration endpoint =
			new AwsClientBuilder.EndpointConfiguration(localStackEndPoint, AWS_REGION);
		BasicAWSCredentials credentials = new BasicAWSCredentials(LOCAL_STACK_ACCESS_KEY, LOCAL_STACK_SECRET_KEY);

		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(endpoint)
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withPathStyleAccessEnabled(true)
			.build();
		amazonS3.createBucket(bucketName);
		return amazonS3;
	}
}
