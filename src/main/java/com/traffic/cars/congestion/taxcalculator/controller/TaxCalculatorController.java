package com.traffic.cars.congestion.taxcalculator.controller;

import com.traffic.cars.congestion.taxcalculator.model.CongestionTaxRequest;
import com.traffic.cars.congestion.taxcalculator.model.CongestionTaxResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;
import com.traffic.cars.congestion.taxcalculator.service.CongestionTaxCalculator;

import static com.traffic.cars.congestion.taxcalculator.utils.RequestTransformer.getVehicle;
import static com.traffic.cars.congestion.taxcalculator.utils.RequestTransformer.getDates;

/**
 * Controller of congestion tax calculator : entry point to call the calculation with different inputs, preferably over HTTP.
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class TaxCalculatorController {
    private final CongestionTaxCalculator congestionTaxCalculator;
    @PostMapping("/congestion-tax")
    public ResponseEntity<?> calculateCongestionTax(@RequestBody CongestionTaxRequest request) throws ResponseStatusException {
        try {
            log.info(">>>>> calculateCongestionTax() service method called with parameters : " + request);
            int taxAmount = congestionTaxCalculator.getTax(getVehicle(request.getVehicleType()), getDates(request.getDates()), request.getCityCode());
            CongestionTaxResponse congestionTaxResponse = new CongestionTaxResponse(taxAmount);
            log.info("<<<<< calculateCongestionTax() service method returned with response: " + congestionTaxResponse);
            return ResponseEntity.ok(congestionTaxResponse);
        } catch (IllegalArgumentException ex) {
            log.error("Bad Request occurred while calculating congestion tax : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (HttpServerErrorException.InternalServerError ex) {
            log.error("Internal Server error occurred while calculating congestion tax : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
