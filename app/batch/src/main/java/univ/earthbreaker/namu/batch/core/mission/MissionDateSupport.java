package univ.earthbreaker.namu.batch.core.mission;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class MissionDateSupport {

	private static final List<LocalDate> SPECIAL_MISSION_DATE;

	static {
		SPECIAL_MISSION_DATE = new ArrayList<>();
		SPECIAL_MISSION_DATE.add(LocalDate.of(Year.MAX_VALUE, 4, 5));
		SPECIAL_MISSION_DATE.add(LocalDate.of(Year.MAX_VALUE, 5, 31));
	}

	private final LocalDate batchDate;

	public MissionDateSupport(LocalDate batchDate) {
		this.batchDate = batchDate;
	}

	boolean isSpecialDate() {
		return SPECIAL_MISSION_DATE.stream()
			.anyMatch(this::isSameDay);
	}

	private boolean isSameDay(@NotNull LocalDate specialDate) {
		return specialDate.getMonthValue() == batchDate.getMonthValue()
			&& specialDate.getDayOfMonth() == batchDate.getDayOfMonth();
	}
}
