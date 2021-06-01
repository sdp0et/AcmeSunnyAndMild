package net.urover.acme;

import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.WeatherState;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSliceTest {
	LocalDateTime time = LocalDateTime.now();
	WeatherForecast forecast;

	@BeforeEach
	void setUp() {
		forecast = createDefaultForeCast();
	}

	@Test
	void fromApiForecast_WARM() {
		Temperature temp = Temperature.withValue(75, "F");
		forecast.setTemperature(temp);

		TimeSlice timeSlice = TimeSlice.fromApiForecast(forecast);

		assertEquals(timeSlice.getTemp(), TimeSlice.TempDescription.WARM);
	}

	@Test
	void fromApiForecast_COOL() {
		Temperature temp = Temperature.withValue(54, "F");
		forecast.setTemperature(temp);

		TimeSlice timeSlice = TimeSlice.fromApiForecast(forecast);

		assertEquals(timeSlice.getTemp(), TimeSlice.TempDescription.COOL);
	}

	@Test
	void fromApiForecast_TEMPERATE() {
		Temperature temp = Temperature.withValue(60, "F");
		forecast.setTemperature(temp);

		TimeSlice timeSlice = TimeSlice.fromApiForecast(forecast);

		assertEquals(timeSlice.getTemp(), TimeSlice.TempDescription.TEMPERATE);
	}

	@Test
	void fromApiForecast_SUNNY() {
		forecast.setWeatherState(weatherStateWithCode(800));

		TimeSlice timeSlice = TimeSlice.fromApiForecast(forecast);

		assertEquals(TimeSlice.Condition.SUNNY, timeSlice.getCondition());
	}

	@Test
	void fromApiForecast_RAINING() {
		forecast.setWeatherState(weatherStateWithCode(210));//light thunderstorm

		TimeSlice timeSlice = TimeSlice.fromApiForecast(forecast);

		assertEquals(TimeSlice.Condition.RAINING, timeSlice.getCondition());
	}

	@Test
	void fromApiForecast_NONE() {
		forecast.setWeatherState(weatherStateWithCode(781)); //tornados are neither sunny no raining by our criteria

		TimeSlice timeSlice = TimeSlice.fromApiForecast(forecast);

		assertEquals(TimeSlice.Condition.NONE, timeSlice.getCondition());
	}



	private WeatherForecast createDefaultForeCast(){
		WeatherForecast forecast = new WeatherForecast();
		forecast.setForecastTime(time);

		Temperature temp = Temperature.withValue(75, "F");
		forecast.setTemperature(temp);

		forecast.setWeatherState(weatherStateWithCode(800));
		return forecast;
	}


	private WeatherState weatherStateWithCode(int code){
		WeatherState weatherState = new WeatherState(code, "name not used", "desc not used");

		return weatherState;
	}

}