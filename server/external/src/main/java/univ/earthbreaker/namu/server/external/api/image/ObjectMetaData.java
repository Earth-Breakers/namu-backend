package univ.earthbreaker.namu.server.external.api.image;

import java.util.Map;
import java.util.TreeMap;

import univ.earthbreaker.namu.server.external.Headers;

public class ObjectMetaData {

	private final Map<String, Object> metadata = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	public ObjectMetaData() {
	}

	public void setContentType(String contentType) {
		metadata.put(Headers.CONTENT_TYPE, contentType);
	}

	public void setContentLength(long contentLength) {
		metadata.put(Headers.CONTENT_LENGTH, contentLength);
	}

	public String getContentType() {
		return (String)metadata.get(Headers.CONTENT_TYPE);
	}

	public long getContentLength() {
		Long contentLength = (Long)metadata.get(Headers.CONTENT_LENGTH);

		if (contentLength == null) {
			return 0;
		}
		return contentLength.longValue();
	}
}
