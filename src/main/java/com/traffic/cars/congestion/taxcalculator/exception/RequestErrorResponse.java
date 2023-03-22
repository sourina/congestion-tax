package com.traffic.cars.congestion.taxcalculator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RequestErrorResponse {
	private String message;
	private long timeStamp;

	public RequestErrorResponse(String message) {
		this.message = message;
		this.timeStamp = System.currentTimeMillis();
	}

}
