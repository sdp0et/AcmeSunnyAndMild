package net.urover.acme;

import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForecastChomper {

	public List<ForecastDay> splitForecastIntoDays(Forecast forecast){
		List<ForecastDay> days = new ArrayList<>();
		for(WeatherForecast dayForecast : forecast.getWeatherForecasts()){
			ForecastDay day = forecastDayForWeatherForecast(days, dayForecast);
			day.addTimeForecast(dayForecast);
		}

		Collections.sort(days);
		return days;
	}

	//find if an Forecast day already exists in the list for the date of the
	// 		given WeatherForecast. If thre isn't one, create it and add to the list.
	private ForecastDay forecastDayForWeatherForecast(List<ForecastDay> days, WeatherForecast timeSlice){
		ForecastDay day = null;
		LocalDate target = timeSlice.getForecastTime().toLocalDate();
		for(ForecastDay d : days){
			if(d.getDate().isEqual(target)){
				day = d;
				break;
			}
		}
		if(day == null){
			day = new ForecastDay(timeSlice.getForecastTime());
			days.add(day);
		}
		return day;
	}


}
