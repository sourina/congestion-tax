package com.traffic.cars.congestion.taxcalculator.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ResponseStatusException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    public ResponseStatusException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}