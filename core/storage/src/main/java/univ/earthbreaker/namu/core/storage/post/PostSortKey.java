package univ.earthbreaker.namu.core.storage.post;

import org.springframework.data.domain.Sort;

public class PostSortKey {

	private static final String SPLITTER = "#";
	private static final int SORT_FIELD_INDEX = 0;
	private static final int SORT_ORDER_INDEX = 1;

	private final String value;

	public PostSortKey(String value) {
		this.value = value;
	}

	Sort getSort() {
		if (value == null) {
			return Sort.unsorted();
		} else {
			return Sort.by(Sort.Direction.valueOf(getSortOrder()), getSortField());
		}
	}

	private String getSortField() {
		return value.split(SPLITTER)[SORT_FIELD_INDEX];
	}

	private String getSortOrder() {
		return value.split(SPLITTER)[SORT_ORDER_INDEX];
	}
}
