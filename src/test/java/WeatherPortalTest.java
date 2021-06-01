import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import net.urover.acme.WeatherPortal;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//some of these tests are for learning the library and would be removed in a final solution
@ExtendWith(MockitoExtension.class)
class WeatherPortalTest {
	static final String DEFAULT_API_KEY = "09110e603c1d5c272f94f64305c09436";
	WeatherPortal portal;
	@Mock OpenWeatherMapClient mockCLient;

	@BeforeEach
	void beforeEach() {
		portal = new WeatherPortal(DEFAULT_API_KEY);
	}

	@Ignore //for learning library
	@Test
	void fiveDaysAsForecast() {
		Forecast forecast = portal.fiveDaysAsForecast();

		System.out.println(forecast.getWeatherForecasts().size());
	}

	@Test
	void fiveDaysAsForecastAsJson() throws IOException {
		String forecast = portal.fiveDaysAsJson();

		String fileName = "src/test/resources/" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replaceAll(":","");
		System.out.println("filename: "+fileName);
		FileOutputStream outputStream = new FileOutputStream(fileName);
		byte[] strToBytes = forecast.getBytes();
		outputStream.write(strToBytes);
		outputStream.close();
	}



}