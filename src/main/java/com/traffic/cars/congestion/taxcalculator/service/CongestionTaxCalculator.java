package com.traffic.cars.congestion.taxcalculator.service;

import com.traffic.cars.congestion.taxcalculator.model.Vehicle;
import com.traffic.cars.congestion.taxcalculator.repository.CityRepository;
import com.traffic.cars.congestion.taxcalculator.repository.TaxRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.traffic.cars.congestion.taxcalculator.entity.City;

import java.sql.Time;
import java.util.*;


@Service
@Slf4j
public class CongestionTaxCalculator {

    private static Map<String, Integer> tollFreeVehicles = new HashMap<>();

    //Config value to control single charge rule
    @Value("${tax.enableSingleRule}")
    private boolean enableSingleRule;

    @Autowired
    private TaxRateRepository taxRateRepository;
    @Autowired
    private CityRepository cityRepository;

    static {
        tollFreeVehicles.put("Motorcycle", 0);
        tollFreeVehicles.put("Bus", 1); // Previously value was Tractor, which changed to Bus, since 'Tractor' is not within the Tax exempt vehicle list in assignment description
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);

    }

    public int getTax(Vehicle vehicle, Date[] dates, String cityCode) throws ResponseStatusException {
        City city = cityRepository.findByCode(cityCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No City found for given City Code : " + cityCode));
        log.debug("City Id : " + city.getId() + " for city code : " + city.getId());

        Date intervalStart = dates[0];
        int totalFee = 0;

        for (int i = 0; i < dates.length; i++) {
            Date date = dates[i];
            int nextFee = GetTollFee(date, vehicle, city.getId());
            int tempFee = GetTollFee(intervalStart, vehicle, city.getId());

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies / 1000 / 60;

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
            }
        }

        /*
         * Note :
         * This configuration value read from properties file to enable/disable the single charge rule for covering up
         * bonus scenario according to the assignment
         */
        if (enableSingleRule) {
            log.debug("Single Charge Rule applied!");
            if (totalFee > 60) totalFee = 60;
        }
        return totalFee;
    }

    private boolean IsTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return tollFreeVehicles.containsKey(vehicleType);
    }

    /*
     * Note :
     * This method was refactored to get the time range from database instead of hard coded values for covering up
     * bonus scenario according to the assignment
     */
    public int GetTollFee(Date date, Vehicle vehicle, long cityId) {
        if (IsTollFreeDate(date) || IsTollFreeVehicle(vehicle)) return 0;
        long time = date.getTime();
        Integer amount = taxRateRepository.findTaxRateSpecificToTimeRange(new Time(time), cityId);
        return amount == null ? 0 : amount;
    }

    private Boolean IsTollFreeDate(Date date) {
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDay() + 1;
        int dayOfMonth = date.getDate();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 2013) {
            if ((month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31))) {
                return true;
            }
        }
        return false;
    }
}
