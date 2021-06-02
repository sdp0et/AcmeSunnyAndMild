package net.urover.acme;

import net.urover.acme.TimeSlice.Condition;
import net.urover.acme.TimeSlice.TempDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSliceSortingHatTest {

	@Test
	void chooseBestTimeSlice_SUNNY_AND_WARM() {
		List<TimeSlice> slices = new ArrayList<>();
		slices.add(createTimeSlice(1, Condition.RAINING, TempDescription.TEMPERATE));
		slices.add(createTimeSlice(2, Condition.NONE, TempDescription.WARM));
		slices.add(createTimeSlice(3, Condition.RAINING, TempDescription.WARM));
		slices.add(createTimeSlice(4, Condition.SUNNY, TempDescription.TEMPERATE));
		slices.add(createTimeSlice(5, Condition.SUNNY, TempDescription.WARM));
		slices.add(createTimeSlice(6, Condition.SUNNY, TempDescription.COOL));
		slices.add(createTimeSlice(7, Condition.NONE, TempDescription.TEMPERATE));

		TimeSliceSortingHat chooser = new TimeSliceSortingHat(slices);
		TimeSlice winner = chooser.chooseBestTimeSlice();

		assertEquals(Condition.SUNNY, winner.condition);
		assertEquals(TempDescription.WARM, winner.temp);
	}

	@Test
	void chooseBestTimeSlice_WARM_NOT_RAINING() {
		List<TimeSlice> slices = new ArrayList<>();
		slices.add(createTimeSlice(1, Condition.RAINING, TempDescription.TEMPERATE));
		slices.add(createTimeSlice(2, Condition.NONE, TempDescription.WARM));
		slices.add(createTimeSlice(3, Condition.RAINING, TempDescription.WARM));
		slices.add(createTimeSlice(4, Condition.SUNNY, TempDescription.TEMPERATE));
		slices.add(createTimeSlice(6, Condition.SUNNY, TempDescription.COOL));
		slices.add(createTimeSlice(7, Condition.NONE, TempDescription.TEMPERATE));

		TimeSliceSortingHat chooser = new TimeSliceSortingHat(slices);
		TimeSlice winner = chooser.chooseBestTimeSlice();

		assertEquals(Condition.NONE, winner.condition);
		assertEquals(TempDescription.WARM, winner.temp);
	}

	@Test
	void chooseBestTimeSlice_TEMPERATE_NOT_RAINING() {
		List<TimeSlice> slices = new ArrayList<>();
		slices.add(createTimeSlice(1, Condition.RAINING, TempDescription.TEMPERATE));
		slices.add(createTimeSlice(2, Condition.RAINING, TempDescription.WARM));
		slices.add(createTimeSlice(3, Condition.RAINING, TempDescription.WARM));
		slices.add(createTimeSlice(4, Condition.SUNNY, TempDescription.TEMPERATE));
		slices.add(createTimeSlice(6, Condition.SUNNY, TempDescription.COOL));
		slices.add(createTimeSlice(7, Condition.NONE, TempDescription.TEMPERATE));

		TimeSliceSortingHat chooser = new TimeSliceSortingHat(slices);
		TimeSlice winner = chooser.chooseBestTimeSlice();
		assertEquals(Condition.SUNNY, winner.condition);
		assertEquals(TempDescription.TEMPERATE, winner.temp);
	}

	@Test
	void chooseBestTimeSlice_RAINING() {
		List<TimeSlice> slices = new ArrayList<>();
		slices.add(createTimeSlice(1, Condition.NONE, TempDescription.COOL));
		slices.add(createTimeSlice(2, Condition.RAINING, TempDescription.WARM));
		slices.add(createTimeSlice(3, Condition.RAINING, TempDescription.COOL));
		slices.add(createTimeSlice(6, Condition.SUNNY, TempDescription.COOL));

		TimeSliceSortingHat chooser = new TimeSliceSortingHat(slices);
		TimeSlice winner = chooser.chooseBestTimeSlice();
		assertEquals(Condition.RAINING, winner.condition);
		assertEquals(TempDescription.WARM, winner.temp);
	}

	@Test
	void chooseBestTimeSlice_COOL() {
		List<TimeSlice> slices = new ArrayList<>();
		slices.add(createTimeSlice(1, Condition.NONE, TempDescription.COOL));
		slices.add(createTimeSlice(2, Condition.NONE, TempDescription.COOL));
		slices.add(createTimeSlice(3, Condition.NONE, TempDescription.COOL));
		slices.add(createTimeSlice(4, Condition.NONE, TempDescription.COOL));
		slices.add(createTimeSlice(6, Condition.SUNNY, TempDescription.COOL));

		TimeSliceSortingHat chooser = new TimeSliceSortingHat(slices);
		TimeSlice winner = chooser.chooseBestTimeSlice();

		assertEquals(Condition.NONE, winner.condition);
		assertEquals(TempDescription.COOL, winner.temp);
	}


	//helpers

	private TimeSlice createTimeSlice(int hourOffset, Condition condition, TempDescription temp){
		LocalDateTime dateTime = LocalDate.now().atStartOfDay();
		dateTime = dateTime.plusHours(hourOffset);
		return CheatyTimeSlice.with(dateTime, condition, temp);
	}


	static class CheatyTimeSlice extends TimeSlice{
		private CheatyTimeSlice() {}
		static public TimeSlice with(LocalDateTime time, Condition condition, TempDescription temp){
			TimeSlice timeSlice =  new TimeSlice();
			timeSlice.time =time;
			timeSlice.condition=condition;
			timeSlice.temp=temp;
			return timeSlice;
		}
	}
}