package com.traffic.cars.congestion.taxcalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.traffic.cars.congestion.taxcalculator.entity.City;

import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	Optional<City> findByCode(String code);
}
