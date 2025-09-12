package univ.earthbreaker.namu.core.domain.mission;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MissionRetrieveStrategyFactory {

	private final Clock koreaTimeClock;

	public MissionRetrieveStrategyFactory(Clock koreaTimeClock) {
		this.koreaTimeClock = koreaTimeClock;
	}

	MissionRetrieveStrategy get() {
		if (SpecialMissionDate.checkTodayIsSpecial(LocalDate.now(koreaTimeClock))) {
			return new FixedAndSpecialMissionRetrieveStrategy();
		} else {
			return new FixedMissionRetrieveStrategy();
		}
	}

	enum SpecialMissionDate {

		TREE_PLANTING_DAY(LocalDate.of(Year.MAX_VALUE, 4, 5)),
		BEACH_COMBING_DAY(LocalDate.of(Year.MAX_VALUE, 5, 31)),
		;

		private final LocalDate date;

		SpecialMissionDate(LocalDate date) {
			this.date = date;
		}

		static boolean checkTodayIsSpecial(@NotNull LocalDate today) {
			return Arrays.stream(values())
				.anyMatch(provideDate -> provideDate.isSameDay(today));
		}

		private boolean isSameDay(@NotNull LocalDate today) {
			return today.getMonthValue() == this.date.getMonthValue()
				&& today.getDayOfMonth() == this.date.getDayOfMonth();
		}

		LocalDate getDate() {
			return date;
		}
	}
}
