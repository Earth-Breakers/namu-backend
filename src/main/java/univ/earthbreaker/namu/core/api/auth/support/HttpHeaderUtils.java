package univ.earthbreaker.namu.core.api.auth.support;

import org.jetbrains.annotations.NotNull;

public class HttpHeaderUtils {

	private HttpHeaderUtils() {
	}

	public static final String AUTHENTICATION_TYPE = "Bearer ";
	public static final String REFRESH_TOKEN = "Refresh-Token";
	public static final String ATTRIBUTE_NAME = "MEMBER_NO";

	public static @NotNull String withBearerToken(String accessToken) {
		return AUTHENTICATION_TYPE.concat(accessToken);
	}
}
