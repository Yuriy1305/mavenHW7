import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

// 30/03/2022, Yuriy Lomtev
// 1. Реализовать корректный вывод информации о текущей погоде. Создать класс WeatherResponse и десериализовать ответ сервера.
//    Выводить пользователю только текстовое описание погоды и температуру в градусах Цельсия.
// 2. Реализовать вывод погоды на следующие 5 дней в формате:
//      | В городе CITY на дату DATE ожидается DAY_WEATHER_TEXT, NIGHT_WEATHER_TEXT,, температура - MIN_TEMPERATURE и MAX_TEMPERATURE |
//  где DATE, DAY_WEATHER_TEXT, NIGHT_WEATHER_TEXT, MIN_TEMPERATURE и MAX_TEMPERATURE - уникальные значения для каждого дня.

    public class Main {
        public static void main(String[] args) {
            try {
// запрос по Питеру 5 дней с полученым ключом и в градусах Цельсия:
                URL weatherUrl = new URL("http://dataservice.accuweather.com/forecasts/v1/daily/5day/295212?apikey=Q9xCitbhbtPpwbQrHTPnvmm1VNa7XrWY&metric=true");
// связываемся с сервером:
                HttpURLConnection urlConnection = (HttpURLConnection) weatherUrl.openConnection();
// при правильном ответе на наш запрос (проверил на странице браузера, см. HW6):
                if (urlConnection.getResponseCode() == 200) {
// пытаемся читать поток и записываем содержание:
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                        StringBuilder responseContent = new StringBuilder();
                        String line = "";
// читаем до конца, т.к. не файл, то ждем null:
                        while ((line = reader.readLine()) != null) {
                            responseContent.append(line);
                        }
// закрываем чтение:
                        reader.close();
// превращаем наш прочитанный JSON в объект для разбора:
                        StringReader forecastJsonReader = new StringReader(responseContent.toString());
                        JsonReader jsonReader = Json.createReader(forecastJsonReader);
                        JsonObject weatherResponseJson = jsonReader.readObject();
// отправляем на разбор и вывод в нужной форме:
                        WeatherResponse weatherResponse = new WeatherResponse(weatherResponseJson);

                    } catch (IOException e) {
                        System.out.println("Ошибка1: " + e.getMessage());
// не забываем закончить связь с сервером:
                    } finally {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception ex) {
                System.out.println("Ошибка2: " + ex.getMessage());
            }
        }
    }


