package ua.bala.citydemo.service;

import lombok.extern.slf4j.Slf4j;
import ua.bala.citydemo.model.City;
import ua.bala.citydemo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public City getById(Long id) {
        log.info("CityServiceImpl: getById {}", id);
        return cityRepository.getOne(id);
    }

    @Override
    public void save(City city) {
        log.info("CityServiceImpl: save {}", city);
        cityRepository.save(city);
    }

    @Override
    public void delete(Long id) {
        log.info("CityServiceImpl: delete {}", id);
        cityRepository.deleteById(id);
    }

    @Override
    public List<City> getAll() {
        log.info("CityServiceImpl: getAll");
        return cityRepository.findAll();
    }
}
