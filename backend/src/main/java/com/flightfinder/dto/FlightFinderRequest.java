package com.flightfinder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FlightFinderRequest {
    @NotBlank(message = "Input string cannot be empty")
    @Size(max = 100, message = "Input string cannot exceed 100 characters")
    @Pattern(regexp = "^[a-z]*$", message = "Only lowercase letters are allowed")
    private String inputString;
}
