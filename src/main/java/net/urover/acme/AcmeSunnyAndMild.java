package net.urover.acme;

import com.github.prominence.openweathermap.api.model.forecast.Forecast;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class AcmeSunnyAndMild {
	public static final String API_KEY = "09110e603c1d5c272f94f64305c09436";


	public static void main(String[] args) {
		WeatherPortal portal = new WeatherPortal(API_KEY);
		Forecast forecast = portal.fiveDaysAsForecast();

		List<ForecastDay> days = new ForecastChomper().splitForecastIntoDays(forecast);
		Collections.sort(days);


		CommunicationSelector selector = new CommunicationSelector();
		for(ForecastDay day : days){
			DayCommunicationSelection dayResult = selector.determineBestContactTimeAndMethod(day);
			System.out.println("For day "+dayResult.getDateString() + " (" + dayResult.getChosenTimeSlice().getTemp() +"/"+dayResult.getChosenTimeSlice().getCondition()+")");
			System.out.println("\t "+dayResult.getChosenMethod() + " between " + dayResult.getTimeRange());
		}



	}
}
