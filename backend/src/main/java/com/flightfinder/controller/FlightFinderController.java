package com.flightfinder.controller;

import com.flightfinder.dto.FlightFinderRequest;
import com.flightfinder.dto.FlightFinderResponse;
import com.flightfinder.dto.SearchHistoryResponse;
import com.flightfinder.service.IFlightFinderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-finder")
//@CrossOrigin(origins = "*")  // Allow CORS (for development)
@RequiredArgsConstructor
@Slf4j
public class FlightFinderController {

    private final IFlightFinderService flightFinderService;

    /**
     * Calculate flight count endpoint
     *
     */
    @PostMapping("/calculate")
    public ResponseEntity<FlightFinderResponse> calculateFlight(
            @Valid @RequestBody FlightFinderRequest request) {

        log.info("Received calculate request: {}", request.getInputString());

        FlightFinderResponse response = flightFinderService.processRequest(
                request.getInputString()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Get search history endpoint
     *
     */
    @GetMapping("/history")
    public ResponseEntity<List<SearchHistoryResponse>> getHistory() {
        log.info("Received get history request");

        List<SearchHistoryResponse> history = flightFinderService.getSearchHistory();

        return ResponseEntity.ok(history);
    }

}
