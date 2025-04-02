package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.Nullable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class RelatedPostRetrieveQuery extends SelfValidating<RelatedPostRetrieveQuery> {

	private final @NotNull Long memberNo;
	private final @NotNull Long relatedMissionNo;
	private final @NotNull Integer page;
	private final @Nullable String sortKey;
	private final Integer size;

	private RelatedPostRetrieveQuery(
		Long memberNo,
		Long relatedMissionNo,
		Integer page,
		Integer size,
		@Nullable String sortKey
	) {
		this.memberNo = memberNo;
		this.relatedMissionNo = relatedMissionNo;
		this.page = page;
		this.size = size;
		this.sortKey = sortKey;
		this.validateSelf("memberNo, relatedMissionNo, page 는 null 이 될 수 없습니다");
	}

	RelatedPostDbQuery toDbQuery() {
		return new RelatedPostDbQuery(memberNo, relatedMissionNo, page, size, sortKey);
	}

	public static RelatedPostRetrieveQuery of(
		Long memberNo,
		Long missionNo,
		Integer page,
		Integer size,
		@Nullable String sortKey
	) {
		Integer sizeValue = new SizeQuery(size).value;
		if (sortKey == null || sortKey.isEmpty()) {
			return new RelatedPostRetrieveQuery(memberNo, missionNo, page, sizeValue, null);
		}
		return new RelatedPostRetrieveQuery(memberNo, missionNo, page, sizeValue, new SortKeyQuery(sortKey).getValue());
	}

	static class SizeQuery extends SelfValidating<SizeQuery> {

		@NotNull
		@Min(4) @Max(10)
		private final Integer value;

		SizeQuery(Integer value) {
			this.value = value;
			this.validateSelf("size 는 최소 4, 최대 10 까지 가능합니다. 또한, null 이 될 수 없습니다");
		}
	}

	static class SortKeyQuery extends SelfValidating<SortKeyQuery> {

		@Pattern(regexp = "\\w*#+(desc|DESC|ASC|asc)\\Z")
		private final String value;

		SortKeyQuery(String value) {
			this.value = value;
			this.validateSelf("정렬 요청 형식은 다음과 같아야 합니다 : {정렬 필드}#{정렬 순서}");
		}

		String getValue() {
			return value.toUpperCase();
		}
	}
}
