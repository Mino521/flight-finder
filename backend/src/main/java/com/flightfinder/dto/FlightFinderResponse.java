package com.flightfinder.dto;

import lombok.Data;

@Data
public class FlightFinderResponse {
    private String inputString;
    private Integer count;
    private String message;

    public FlightFinderResponse(String inputString, Integer count) {
        this.inputString = inputString;
        this.count = count;
        this.message = "Success";
    }
}
