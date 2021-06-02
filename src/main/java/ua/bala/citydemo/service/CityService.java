package ua.bala.citydemo.service;

import ua.bala.citydemo.model.City;

import java.util.List;

public interface CityService {

    City getById(Long id);

    void save(City city);

    void delete(Long id);

    List<City> getAll();
}
