package net.urover.acme;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;

public class WeatherPortal {
	private OpenWeatherMapClient client;


	public WeatherPortal(String apiKey) {
		this.client = new OpenWeatherMapClient(apiKey);
	}

	public WeatherPortal(OpenWeatherMapClient client) {
		this.client = client;
	}

	public Forecast fiveDaysAsForecast(){
		return client.forecast5Day3HourStep().byCityName("minneapolis,us").unitSystem(UnitSystem.IMPERIAL).retrieve().asJava();
	}


	public String fiveDaysAsJson(){
		return client.forecast5Day3HourStep().byCityName("minneapolis,us").unitSystem(UnitSystem.IMPERIAL).retrieve().asJSON();
	}

}
