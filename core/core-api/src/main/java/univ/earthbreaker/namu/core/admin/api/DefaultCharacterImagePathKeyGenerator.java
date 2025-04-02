// package univ.earthbreaker.namu.core.admin.api;
//
// import java.util.UUID;
//
// import org.jetbrains.annotations.NotNull;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
//
// import univ.earthbreaker.namu.external.aws.image.ImagePathKeyGenerator;
//
// @Component(value = "defaultCharacterImagePathGen")
// public class DefaultCharacterImagePathKeyGenerator implements ImagePathKeyGenerator {
//
// 	private static final String CHARACTER_IMAGE_PATH_KEY_FORMAT = "%s/%s_%s_%s";
//
// 	private final String characterImageDir;
//
// 	public DefaultCharacterImagePathKeyGenerator(@Value("${image.character-dir}") String characterImageDir) {
// 		this.characterImageDir = characterImageDir;
// 	}
//
// 	@Override
// 	public @NotNull String generate(String adminKey, String originFileName) {
// 		return String.format(
// 			CHARACTER_IMAGE_PATH_KEY_FORMAT,
// 			characterImageDir,
// 			adminKey,
// 			UUID.randomUUID(),
// 			originFileName
// 		);
// 	}
// }
