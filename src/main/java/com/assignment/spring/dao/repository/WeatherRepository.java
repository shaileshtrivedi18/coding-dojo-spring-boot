package com.assignment.spring.dao.repository;

import com.assignment.spring.dao.entity.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {
}
