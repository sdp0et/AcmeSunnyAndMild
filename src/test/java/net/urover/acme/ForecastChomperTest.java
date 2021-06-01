package net.urover.acme;

import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class ForecastChomperTest {

	private static final String DEFAULT_FORECAST_FILE = "basic_forecast_2021-06-01T133111.509.json";
	private final TestForecastCreator testForecastCreator = new TestForecastCreator();
	Forecast forecast;
	ForecastChomper chomper;

	@BeforeEach
	void setUp() throws IOException {
		forecast = testForecastCreator.forecastFromFile(DEFAULT_FORECAST_FILE);
		chomper = new ForecastChomper();
	}

	@Test
	void splitForecastIntoDays() throws IOException {
		assertEquals(40, forecast.getWeatherForecasts().size());

		List<ForecastDay> forecastDays = chomper.splitForecastIntoDays(forecast);
		assertEquals(6, forecastDays.size());  //sample file starts in middle of day so actually spans 6 calendar days
		assertEquals(3, forecastDays.get(0).getTimeSliceCount());
		assertEquals(8, forecastDays.get(1).getTimeSliceCount());
		assertEquals(8, forecastDays.get(2).getTimeSliceCount());
		assertEquals(8, forecastDays.get(3).getTimeSliceCount());
		assertEquals(8, forecastDays.get(4).getTimeSliceCount());
		assertEquals(5, forecastDays.get(5).getTimeSliceCount());

		assertTrue(forecastDays.get(0).getDate().isBefore(forecastDays.get(1).getDate()));
		assertTrue(forecastDays.get(1).getDate().isBefore(forecastDays.get(2).getDate()));
		assertTrue(forecastDays.get(2).getDate().isBefore(forecastDays.get(3).getDate()));
		assertTrue(forecastDays.get(3).getDate().isBefore(forecastDays.get(4).getDate()));


	}




}