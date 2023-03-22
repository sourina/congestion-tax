package com.traffic.cars.congestion.taxcalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.traffic.cars.congestion.taxcalculator.entity.TaxRate;

import java.sql.Time;


@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
	/**
	 *
	 * @param inputTime : user input time for tax calculation
	 * @param cityId : epected city for tax calculation
	 * @return tax_amount for input_time based on the city
	 */
	@Query(value = "SELECT t.amount FROM taxrate AS t WHERE t.start_time <=:inputTime AND t.end_time > :inputTime AND t.city_Id=:cityId", nativeQuery = true)
	Integer findTaxRateSpecificToTimeRange(@Param("inputTime") Time inputTime, @Param("cityId") long cityId);

}
