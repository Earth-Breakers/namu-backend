package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.NotNull;

import univ.earthbreaker.namu.core.domain.common.NotFoundException;

public class PostNotFoundException extends NotFoundException {

	private PostNotFoundException(String domainName) {
		super(domainName);
	}

	public static @NotNull PostNotFoundException notFound() {
		return new PostNotFoundException("게시글");
	}
}
