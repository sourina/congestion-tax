package com.traffic.cars.congestion.taxcalculator.service;

import com.traffic.cars.congestion.taxcalculator.entity.City;
import com.traffic.cars.congestion.taxcalculator.model.Car;
import com.traffic.cars.congestion.taxcalculator.repository.CityRepository;
import com.traffic.cars.congestion.taxcalculator.repository.TaxRateRepository;
import com.traffic.cars.congestion.taxcalculator.utils.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class CongestionTaxCalculatorTest {
    @Mock
    private TaxRateRepository taxRateRepository;
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CongestionTaxCalculator congestionTaxCalculator;

    @Test
    public void testCalculateTax_Success() throws ParseException {
        // create a new city
        City city = new City(1, "Gothenburg", "GOT");
        // mock the cityRepository.findByCode() method to return the created city
        Mockito.when(cityRepository.findByCode(Mockito.any(String.class))).thenReturn(Optional.of(city));

        // mock the taxRateRepository.findTaxRateSpecificToTimeRange() method to return a tax amount
        Mockito.when(taxRateRepository.findTaxRateSpecificToTimeRange(Mockito.any(Time.class), Mockito.any(Long.class))).thenReturn(29);

        // call the getTax() method and assert the returned user
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);// format: "yyyy-MM-dd HH:mm:ss"
        Date date1 = format.parse("2013-01-14 21:00:00");
        Date date2 = format.parse("2013-02-08 14:35:00");

        Date[] dates = new Date[2];

        dates[0] = date1;
        dates[1] = date2;

        int taxAmount = congestionTaxCalculator.getTax(new Car(), dates, "GOB");
        assertEquals(58, taxAmount);
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetUserById_ThrowsNotFoundException() throws ParseException {

        // call the getTax() method, should throw ResponseStatusException
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);// format: "yyyy-MM-dd HH:mm:ss"
        Date date1 = format.parse("2013-01-14 21:00:00");
        Date date2 = format.parse("2013-02-08 14:35:00");
        Date[] dates = new Date[2];
        dates[0] = date1;
        dates[1] = date2;

        congestionTaxCalculator.getTax(new Car(), dates, null);
    }
}
