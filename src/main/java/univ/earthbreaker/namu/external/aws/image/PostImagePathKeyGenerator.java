package univ.earthbreaker.namu.external.aws.image;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostImagePathKeyGenerator implements ImagePathKeyGenerator {

	private static final String POST_IMAGE_PATH_KEY_FORMAT = "%s/%s_%s_%s";

	private final String postImageDir;

	public PostImagePathKeyGenerator(@Value("${image.post-dir}") String postImageDir) {
		this.postImageDir = postImageDir;
	}

	@Override
	public @NotNull String generate(long memberNo, String originFileName) {
		return String.format(
			POST_IMAGE_PATH_KEY_FORMAT,
			postImageDir,
			memberNo,
			UUID.randomUUID(),
			originFileName
		);
	}
}
