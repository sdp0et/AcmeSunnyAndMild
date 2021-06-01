package net.urover.acme;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ForecastDay implements Comparable<ForecastDay>{
//	private List<WeatherForecast> timeSlices = new ArrayList<>();
	private List<WeatherForecast> timeSlices = new ArrayList<>();
	private LocalDate date;
	static public DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE;


	//may be overthinking safety for first draft.  Chill out, Paul.
	public void addTimeForecast(WeatherForecast forecast){
		if(isSameDate(forecast)) {
			timeSlices.add(forecast);
		}else{
			throw new IllegalArgumentException("Tried to add forecast with date ["+
					forecast.getForecastTime().format(dateTimeFormat));
		}
	}

	private ForecastDay() {}

	public ForecastDay(LocalDateTime dateTime) {
		this.date = dateTime.toLocalDate();
	}

	public boolean isSameDate(WeatherForecast forecast){
		return forecast.getForecastTime().toLocalDate().equals(date);
	}

	@Override
	public int compareTo(ForecastDay o) {
		return date.compareTo(o.getDate());
	}

	//access
	public List<WeatherForecast> getTimeSlices() {
		return timeSlices;
	}

	public int getTimeSliceCount(){
		return timeSlices.size();
	}

	public LocalDate getDate() {
		return date;
	}

}
