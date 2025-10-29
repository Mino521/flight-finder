package com.flightfinder.service.impl;

import com.flightfinder.dto.FlightFinderResponse;
import com.flightfinder.dto.SearchHistoryResponse;
import com.flightfinder.entity.SearchHistoryEntity;
import com.flightfinder.repository.SearchHistoryRepository;
import com.flightfinder.service.IFlightFinderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightFinderServiceImpl implements IFlightFinderService {

    private final SearchHistoryRepository searchHistoryRepository;

    /**
     * Process flight finder request and save to history
     *
     * @param inputString
     */
    @Override
    public FlightFinderResponse processRequest(String inputString) {
        log.info("Processing flight finder request: {}", inputString);

        int count = calculateFlightCount(inputString);

        SearchHistoryEntity searchHistoryEntity = new SearchHistoryEntity();
        searchHistoryEntity.setInputString(inputString);
        searchHistoryEntity.setResultCount(count);
        searchHistoryRepository.save(searchHistoryEntity);

        log.info("Saved search history with result: {}", count);

        return new FlightFinderResponse(inputString, count);
    }

    /**
     * Calculate how many times "flight" can be formed
     */
    private int calculateFlightCount(String inputString) {
        log.debug("Calculating flight count for input: {}", inputString);

        // Characters in "flight"
        Set<Character> flightChars = Set.of('f', 'l', 'i', 'g', 'h', 't');

        // Count each character
        Map<Character, Long> charCount = inputString.chars()
                .mapToObj(c -> (char) c)
                .filter(flightChars::contains)  // filter only relevant chars
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        // Find minimum
        return flightChars.stream()
                .mapToInt(c -> charCount.getOrDefault(c, 0L).intValue())
                .min()
                .orElse(0);
    }

    /**
     * Get all search history
     */
    @Override
    public List<SearchHistoryResponse> getSearchHistory() {
        log.debug("Fetching all search history");

        return searchHistoryRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertSearchHistoryToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert SearchHistory entity to DTO SearchHistoryResponse
     */
    private SearchHistoryResponse convertSearchHistoryToDTO(SearchHistoryEntity searchHistoryEntity) {
        return new SearchHistoryResponse(
                searchHistoryEntity.getId(),
                searchHistoryEntity.getInputString(),
                searchHistoryEntity.getResultCount(),
                searchHistoryEntity.getCreatedAt()
        );
    }
}
