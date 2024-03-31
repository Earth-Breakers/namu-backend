package univ.earthbreaker.namu.core.domain.mission;

public enum MissionActivity {

	/**
	 * 기본 미션 MissionType.DEFAULT
	 */
	USE_PUBLIC_TRANSPORT("대중교통 이용하기 (따릉이 포함)"),
	RECYCLE("분리수거 하기"),
	USE_STAIRS("엘리베이터 대신 계단 이용하기"),
	USE_DIGITAL_RECEIPTS("종이 영수증 대신 스마트 영수증 받기"),
	USE_TUMBLER("텀블러 사용하기"),
	UNPLUG_ELECTRONICS("사용하지 않는 전기 코드 뽑기"),

	/**
	 * 오늘의 미션 MissionType.TODAY
	 */
	USE_REUSABLE_CONTAINERS("다회용기에 음식 포장하기"),
	VISIT_VEGAN_CAFE("비건 카페 가기"),
	USE_SHOPPING_BAGS("장바구니 이용하기"),
	BUY_LOCAL_PRODUCE("지역 농산품 구매하기"),
	USE_ECO_FRIENDLY_PRODUCTS("친환경 제품 사용하기"),
	VISIT_REFILL_STORES("리필샵 방문하기"),
	PLOGGING("플로깅"),

	/**
	 * 스페셜 미션 MissionType.SPECIAL
	 */
	PLANT_TREES("나무 심기"),
	BEACH_COMBING("비치코밍")
	;

	private final String value;

	MissionActivity(String value) {
		this.value = value;
	}
}
