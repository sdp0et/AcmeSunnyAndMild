package net.urover.acme;

import com.github.prominence.openweathermap.api.enums.WeatherCondition;
import com.github.prominence.openweathermap.api.model.Temperature;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import groovy.lang.Range;

import java.time.LocalDateTime;

public class TimeSlice implements Comparable<TimeSlice>{
	enum TempDescription {
		WARM,
		TEMPERATE,
		COOL;
	}
	enum Condition{
		SUNNY,
		RAINING,
		NONE;
	}

	protected LocalDateTime time;
	protected TempDescription temp;
	protected Condition condition;
	protected WeatherForecast apiForecast;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TimeSlice{");
		sb.append("time=").append(time);
		sb.append(", temp=").append(temp);
		sb.append(", condition=").append(condition);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public int compareTo(TimeSlice o) {
		return time.compareTo(o.getTime());
	}

	//construction
	protected TimeSlice(){}

	static public TimeSlice fromApiForecast(WeatherForecast forecast){
		TimeSlice timeSlice =  new TimeSlice();
		timeSlice.time = forecast.getForecastTime();
		timeSlice.apiForecast=forecast;

		Temperature temp = forecast.getTemperature();
		if(temp.getValue() >= 75d){
			timeSlice.temp=TempDescription.WARM;
		}else if(temp.getValue() < 55d ){
			timeSlice.temp=TempDescription.COOL;
		}else timeSlice.temp=TempDescription.TEMPERATE;

		timeSlice.condition = getWeatherCondition(forecast);

		return timeSlice;
	}



	private static Condition getWeatherCondition(WeatherForecast forecast) {
		int weatherStateId = forecast.getWeatherState().getId();

		if(forecast.getWeatherState().getWeatherConditionEnum() == WeatherCondition.CLEAR){
			return Condition.SUNNY;
		}else if(isRaining(forecast.getWeatherState().getId())){
			return Condition.RAINING;
		}else return Condition.NONE;

	}

	//UGH! separated into multiple conditionals for sanity/readability
	//from com.github.prominence.openweathermap.api.enums.WeatherCondition
	private static boolean isRaining(int conditionId) {
		if(conditionId > 200 && conditionId < 600){
			return true;
		}//not counting snow as rain, but since that implies temperature is in the range we'd do the same thing as rain
		// 		anyway it may not matter. Totally ignoring tornados and fog and sandstorms for now.
		else return false;
	}


//	public boolean

	public LocalDateTime getTime() {
		return time;
	}

	public TempDescription getTemp() {
		return temp;
	}

	public Condition getCondition() {
		return condition;
	}

	public WeatherForecast getApiForecast() {
		return apiForecast;
	}
}
