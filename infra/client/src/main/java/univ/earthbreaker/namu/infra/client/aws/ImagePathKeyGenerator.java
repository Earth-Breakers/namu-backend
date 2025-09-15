package univ.earthbreaker.namu.infra.client.aws;

import org.jetbrains.annotations.NotNull;

public interface ImagePathKeyGenerator {
	@NotNull String generate(String memberKey, String originFileName);
}
