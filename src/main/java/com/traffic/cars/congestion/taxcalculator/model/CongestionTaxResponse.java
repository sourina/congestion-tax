package com.traffic.cars.congestion.taxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * * CongestionTaxResponse to model the response for user from entrypoint at controller layer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongestionTaxResponse {
    int congestionTaxFee;
}
