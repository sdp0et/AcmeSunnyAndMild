package net.urover.acme;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static net.urover.acme.DayCommunicationSelection.CommunicationMethod.EMAIL;
import static net.urover.acme.DayCommunicationSelection.CommunicationMethod.PHONE;
import static net.urover.acme.DayCommunicationSelection.CommunicationMethod.TEXT;
import static org.junit.jupiter.api.Assertions.*;

class CommunicationSelectorTest {

	CommunicationSelector selector;

	@BeforeEach
	void setUp() {
		selector = new CommunicationSelector();
	}

	@Test
	void determineBestContactTimeAndMethod_SUNNY_WARM() {
		ForecastDay day = createForecastDayWith(TimeSlice.Condition.SUNNY, TimeSlice.TempDescription.WARM);
		DayCommunicationSelection winner = selector.determineBestContactTimeAndMethod(day);
		assertEquals(TEXT, winner.getChosenMethod());
	}

	@Test
	void determineBestContactTimeAndMethod_RAIN() {
		ForecastDay day = createForecastDayWith(TimeSlice.Condition.RAINING, TimeSlice.TempDescription.TEMPERATE);
		DayCommunicationSelection winner = selector.determineBestContactTimeAndMethod(day);
		assertEquals(PHONE, winner.getChosenMethod());
	}

	@Test
	void determineBestContactTimeAndMethod_COOL() {
		ForecastDay day = createForecastDayWith(TimeSlice.Condition.SUNNY, TimeSlice.TempDescription.COOL);
		DayCommunicationSelection winner = selector.determineBestContactTimeAndMethod(day);
		assertEquals(PHONE, winner.getChosenMethod());
	}

	@Test
	void determineBestContactTimeAndMethod_TEMPERATE() {
		ForecastDay day = createForecastDayWith(TimeSlice.Condition.NONE, TimeSlice.TempDescription.TEMPERATE);
		DayCommunicationSelection winner = selector.determineBestContactTimeAndMethod(day);
		assertEquals(EMAIL, winner.getChosenMethod());
	}


	//helpers

	private ForecastDay createForecastDayWith(TimeSlice.Condition condition, TimeSlice.TempDescription temp) {
		CheatyForecastDay day = new CheatyForecastDay(LocalDateTime.now());
		day.addTimeSlice(createTimeSlice(1, condition, temp));
		return day;
	}

	private TimeSlice createTimeSlice(int hourOffset, TimeSlice.Condition condition, TimeSlice.TempDescription temp){
		LocalDateTime dateTime = LocalDate.now().atStartOfDay();
		dateTime = dateTime.plusHours(hourOffset);
		return TimeSliceSortingHatTest.CheatyTimeSlice.with(dateTime, condition, temp);
	}



	//let us create a ForecastDay without having to create an entire api forecast
	static class CheatyForecastDay extends ForecastDay{
		public CheatyForecastDay(LocalDateTime dateTime) {
			super(dateTime);
		}
		public void addTimeSlice(TimeSlice timeSlice){
			super.timeSlices.add(timeSlice);
		}
	}
}