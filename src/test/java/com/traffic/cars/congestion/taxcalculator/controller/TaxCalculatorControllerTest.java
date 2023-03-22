package com.traffic.cars.congestion.taxcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic.cars.congestion.taxcalculator.exception.RequestExceptionHandler;
import com.traffic.cars.congestion.taxcalculator.model.CongestionTaxRequest;
import com.traffic.cars.congestion.taxcalculator.model.Vehicle;
import com.traffic.cars.congestion.taxcalculator.service.CongestionTaxCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class TaxCalculatorControllerTest {
    @Mock
    private CongestionTaxCalculator congestionTaxCalculator;

    @InjectMocks
    private TaxCalculatorController taxCalculatorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taxCalculatorController).setControllerAdvice(new RequestExceptionHandler())
                .build();
    }

    @Test
    public void testCalculateTax_Success() throws Exception {
        // create a new congestionTaxRequest with valid parameters
        List<String> dates = new ArrayList<>();
        dates.add("2013-01-14 21:00:00");
        dates.add("2013-02-08 14:35:00");
        CongestionTaxRequest congestionTaxRequest = new CongestionTaxRequest("car", dates, "GOB");

        // mock the tax calculator service getTax() method to return a tax amount
        Mockito.when(congestionTaxCalculator.getTax(Mockito.any(Vehicle.class), Mockito.any(),  Mockito.any(String.class))).thenReturn(26);

        // perform POST request to /congestion-tax endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/congestion-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(congestionTaxRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void testCalculateTax_ThrowsBadRequestException() throws Exception {
        // create a new congestionTaxRequest with null parameters
        CongestionTaxRequest congestionTaxRequest = new CongestionTaxRequest(null, null, "");

        // perform POST request to /users endpoint with user request body
        mockMvc.perform(MockMvcRequestBuilders.post("/congestion-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(congestionTaxRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testCalculateTax_ThrowsNotFoundException() throws Exception {
        // create a new congestionTaxRequest with empty parameters
        CongestionTaxRequest congestionTaxRequest = new CongestionTaxRequest("", null, "");
        // perform POST request to /users endpoint with user request body
        mockMvc.perform(MockMvcRequestBuilders.post("/congestion-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(congestionTaxRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // TODO: Add more test cases to cover up more exception scenarios
}
