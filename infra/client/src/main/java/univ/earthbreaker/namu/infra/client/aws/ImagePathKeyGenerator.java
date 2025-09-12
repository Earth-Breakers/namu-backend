package univ.earthbreaker.namu.external.aws.image;

import org.jetbrains.annotations.NotNull;

public interface ImagePathKeyGenerator {
	@NotNull String generate(String memberKey, String originFileName);
}
