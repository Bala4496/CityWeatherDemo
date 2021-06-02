package ua.bala.citydemo.rest;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ua.bala.citydemo.model.City;
import ua.bala.citydemo.model.Weather;
import ua.bala.citydemo.service.CityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cities/")
public class CityRestController {

    private static final String WEATHER_API = "0c612a34ee0e22bef3b87827b64e5204";

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> getCity(@PathVariable("id") Long cityId){
        if(cityId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        City city = this.cityService.getById(cityId);

        if(city == null){
            System.out.println("City == NULL");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(city, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/{id}/weather/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Weather> getCurrentWeatherById(@PathVariable("id") Long cityId) {

        if(cityId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        City city = this.cityService.getById(cityId);

        if(city == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final String uri = "http://api.openweathermap.org/data/2.5/weather?q="+city.getName()+"&units=metric&appid="+WEATHER_API;

        String jsonStr = new RestTemplate().getForObject(uri, String.class);
        Map jsonJavaRootObject = new Gson().fromJson(jsonStr, Map.class);
        String weatherDescription = ((Map)((ArrayList)jsonJavaRootObject.get("weather")).get(0)).get("description").toString();
        Map jsonJavaMain = (Map) jsonJavaRootObject.get("main");
        Double weatherTemperature = Double.parseDouble(jsonJavaMain.get("temp").toString());
        Double weatherFeels = Double.parseDouble(jsonJavaMain.get("feels_like").toString());
        Map jsonJavaWind = (Map) jsonJavaRootObject.get("wind");
        Double weatherWind = Double.parseDouble(jsonJavaWind.get("speed").toString());

        Weather weather = new Weather(weatherDescription, weatherTemperature, weatherFeels, weatherWind);
        return new ResponseEntity<>(weather, HttpStatus.FOUND);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> saveCity(@RequestBody @Validated City city){

        if(city == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.cityService.save(city);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> updateCity(@RequestBody @Validated City city, UriComponentsBuilder builder){

        if(city == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.cityService.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> deleteCity(@PathVariable("id") Long cityId){

        City city = this.cityService.getById(cityId);

        if(city == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.cityService.delete(cityId);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<City>> getAllCities(){

        List<City> cities = this.cityService.getAll();

        if(cities == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cities, HttpStatus.FOUND);
    }

}
