package univ.earthbreaker.namu.core.domain.point;

public class EnergyPointFixture {

	public static final long MEMBER_NO = 1L;
	public static final int MEMBER_POINT_VALUE = 100;
	public static final Energy ENERGY = Energy.of(1, MEMBER_NO, MEMBER_POINT_VALUE);
	public static final Energy EMPTY_ENERGY = Energy.of(1, MEMBER_NO, 0);

	public static final int USE_POINT_VALUE = 99;
	public static final int USE_OVER_POINT_VALUE = 101;
	public static final String ENERGY_TYPE = "BEAUTY";
}
