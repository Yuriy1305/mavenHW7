import javax.json.JsonArray;
import javax.json.JsonObject;

public class WeatherResponse {
    private String date;
    private double minimumTemperature;
    private double maximumTemperature;
    private String dayTextDescription;
    private String nightTextDescription;
    
// немного сократил и упростил для своего понимания:
    public WeatherResponse(JsonObject jsonObject) {
        System.out.println("Погода на 5 дней в городе Saint Petersburg:");
// сначала выделяем отдельные дни:
        JsonArray jsonDailyForecastsArray = jsonObject.getJsonArray("DailyForecasts");
        for (int forecast = 0; forecast < jsonDailyForecastsArray.size(); forecast++) {
            JsonObject jsonForecast = jsonDailyForecastsArray.getJsonObject(forecast);
// потом разбираем каждый день по ключам:
            date = jsonForecast.getString("Date");
            minimumTemperature = jsonForecast
                    .getJsonObject("Temperature")
                    .getJsonObject("Minimum")
                    .getJsonNumber("Value")
                    .doubleValue();
            maximumTemperature = jsonForecast
                    .getJsonObject("Temperature")
                    .getJsonObject("Maximum")
                    .getJsonNumber("Value")
                    .doubleValue();
            dayTextDescription = jsonForecast
                    .getJsonObject("Day")
                    .getString("IconPhrase");
            nightTextDescription = jsonForecast
                    .getJsonObject("Night")
                    .getString("IconPhrase");

            System.out.println(date.substring(0, 10) + "-го ожидается днём " + dayTextDescription +
                    ", ночью " + nightTextDescription +
                    " при минимальной температауре " + minimumTemperature +
                    " и максимальной " + maximumTemperature + "°C.");
       }
    }
}


