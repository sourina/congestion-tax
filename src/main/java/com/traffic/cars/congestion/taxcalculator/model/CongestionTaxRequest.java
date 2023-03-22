package com.traffic.cars.congestion.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * CongestionTaxRequest to model the user input for entrypoint at controller layer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongestionTaxRequest {
    String vehicleType;
    List<String> dates;
    String cityCode;


}
