package net.LearnSpringBoot.journalApp.Service;

import net.LearnSpringBoot.journalApp.ApiResponse.weatherResponse;
import net.LearnSpringBoot.journalApp.Cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiService {

@Value("${weather.api.key}")
    private  String Apikey ;

    @Autowired
  private   RestTemplate restTemplate;

    @Autowired
    private weatherResponse weatherResponse;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public weatherResponse getWeather(String city) {
        weatherResponse weatherResponse = redisService.get("weather of _" + city, weatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String FINALAPI = appCache.APP_CACHE.get("weather_api").replace("<city>", city).replace("<apikey>", Apikey);
            ResponseEntity<weatherResponse> response = restTemplate.exchange(FINALAPI, HttpMethod.GET, null, weatherResponse.class);
            weatherResponse body = response.getBody();
            if(body!=null){
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }



    }
    }


