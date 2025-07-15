// package univ.earthbreaker.namu.app.api.mission;
//
// import java.util.UUID;
//
// import org.jetbrains.annotations.NotNull;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
//
// import univ.earthbreaker.namu.external.aws.image.ImagePathKeyGenerator;
//
// @Component(value = "missionPostImagePathGen")
// public class MissionPostImagePathKeyGenerator implements ImagePathKeyGenerator {
//
// 	private static final String POST_IMAGE_PATH_KEY_FORMAT = "%s/%s_%s_%s";
//
// 	private final String postImageDir;
//
// 	public MissionPostImagePathKeyGenerator(@Value("${image.post-dir}") String postImageDir) {
// 		this.postImageDir = postImageDir;
// 	}
//
// 	@Override
// 	public @NotNull String generate(String memberKey, String originFileName) {
// 		return String.format(
// 			POST_IMAGE_PATH_KEY_FORMAT,
// 			postImageDir,
// 			memberKey,
// 			UUID.randomUUID(),
// 			originFileName
// 		);
// 	}
// }
