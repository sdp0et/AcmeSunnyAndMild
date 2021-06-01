package net.urover.acme;

import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.request.forecast.free.FiveDayThreeHourStepForecastResponseMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TestForecastCreator {
	public TestForecastCreator() {
	}

	Forecast forecastFromFile(String fileName) throws IOException {
		InputStream is = getClass().getClassLoader().getResource(fileName).openStream();

		String forecastJson = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
				.lines().collect(Collectors.joining("\n"));
		return forecastFromJson(forecastJson);
	}

	Forecast forecastFromJson(String jsonForecast) {
		return new FiveDayThreeHourStepForecastResponseMapper(UnitSystem.IMPERIAL).mapToForecast(jsonForecast);
	}
}