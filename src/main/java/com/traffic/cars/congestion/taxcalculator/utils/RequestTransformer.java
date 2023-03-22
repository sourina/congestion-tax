package com.traffic.cars.congestion.taxcalculator.utils;

import com.traffic.cars.congestion.taxcalculator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class using to perform the initial transformation of input data for tax calculation:
 *  1. Get vehicle type
 *  2. Get dates
 */
public final class RequestTransformer {

    public static Vehicle getVehicle(String vehicleString) throws ResponseStatusException {

        Optional<String> vehicleType = Optional.ofNullable(vehicleString);

        if(vehicleType.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No vehicle type provided in the request.");

        switch (vehicleType.get().toLowerCase())
        {
            case "motorcycle":
                return new Motorcycle();
            case "bus":
                return new Bus();
            case "emergency":
                return new Emergency();
            case "diplomat":
                return new Diplomat();
            case "foreign":
                return new Foreign();
            case "military":
                return new Military();
            case "car":
                return new Car();
            case "van":
                return new Van();
            case "lorry":
                return new Lorry();
            default:
                System.out.println("Invalid Input!");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Matching vehicle type found for given input : " + vehicleString);
        }
    }

    public static Date[] getDates(List<String> datesInString) {

        if(datesInString.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data for dates provided.");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        List<Date> dates = datesInString.stream()
                .map( date -> {
                    try {
                        return simpleDateFormat.parse(date);
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format found in : " + date);
                    }
                })
                .collect(Collectors.toList() );
        return  dates.stream().toArray(Date[]::new);
    }
}
