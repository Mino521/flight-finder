package com.flightfinder.service;

import com.flightfinder.dto.FlightFinderResponse;
import com.flightfinder.dto.SearchHistoryResponse;

import java.util.List;

public interface IFlightFinderService {

    /**
     * Process flight finder request and save to history
     *
     * @param inputString
     */
    FlightFinderResponse processRequest(String inputString);

    /**
     * Get all search history
     */
    List<SearchHistoryResponse> getSearchHistory();

}
