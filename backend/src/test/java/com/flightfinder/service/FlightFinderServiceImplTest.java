package com.flightfinder.service;

import com.flightfinder.dto.FlightFinderResponse;
import com.flightfinder.dto.SearchHistoryResponse;
import com.flightfinder.entity.SearchHistoryEntity;
import com.flightfinder.repository.SearchHistoryRepository;
import com.flightfinder.service.impl.FlightFinderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Flight Finder Service Tests")
public class FlightFinderServiceImplTest {
    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private FlightFinderServiceImpl flightFinderService;

    private SearchHistoryEntity searchHistory1;
    private SearchHistoryEntity searchHistory2;

    @BeforeEach
    void setUp() {
        // Prepare test data
        searchHistory1 = new SearchHistoryEntity();
        searchHistory1.setId(1L);
        searchHistory1.setInputString("flight");
        searchHistory1.setResultCount(1);
        searchHistory1.setCreatedAt(LocalDateTime.now());

        searchHistory2 = new SearchHistoryEntity();
        searchHistory2.setId(2L);
        searchHistory2.setInputString("flightflight");
        searchHistory2.setResultCount(2);
        searchHistory2.setCreatedAt(LocalDateTime.now().minusMinutes(1));
    }

    // ========== processRequest Tests (Indirectly tests calculateFlightCount) ==========

    @Test
    @DisplayName("Should calculate and return 1 for 'flight'")
    void testProcessRequest_SingleFlight() {
        // Given
        String input = "flight";

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertNotNull(response);
        assertEquals("flight", response.getInputString());
        assertEquals(1, response.getCount());
        assertEquals("Success", response.getMessage());

        // Verify save was called with correct data
        verify(searchHistoryRepository).save(argThat(history ->
                history.getInputString().equals("flight") &&
                        history.getResultCount() == 1
        ));
    }

    @Test
    @DisplayName("Should calculate and return 2 for 'flightflight'")
    void testProcessRequest_DoubleFlight() {
        // Given
        String input = "flightflight";

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(2, response.getCount());

        verify(searchHistoryRepository).save(argThat(history ->
                history.getResultCount() == 2
        ));
    }

    @Test
    @DisplayName("Should calculate and return 2 for 'fflliigghhtt'")
    void testProcessRequest_DoubleCharacters() {
        // Given
        String input = "fflliigghhtt";

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(2, response.getCount());
    }

    @Test
    @DisplayName("Should calculate and return 1 for input with extra characters")
    void testProcessRequest_WithExtraCharacters() {
        // Given
        String input = "lightfabc";

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(1, response.getCount());
    }

    @Test
    @DisplayName("Should calculate and return 0 when input cannot form 'flight'")
    void testProcessRequest_CannotForm() {
        // Given
        String input = "abc";

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(0, response.getCount());
    }

    @Test
    @DisplayName("Should calculate and return 0 for empty string")
    void testProcessRequest_EmptyString() {
        // Given
        String input = "";

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(0, response.getCount());
    }

    @Test
    @DisplayName("Should handle scrambled characters")
    void testProcessRequest_ScrambledCharacters() {
        // Given
        String input = "tghilf"; // Scrambled 'flight'

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(1, response.getCount());
    }

    @Test
    @DisplayName("Should handle bottleneck character correctly")
    void testProcessRequest_Bottleneck() {
        // Given
        String input = "fllliiiggghhhttt"; // Only 1 'f'

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertEquals(1, response.getCount());
    }

    @Test
    @DisplayName("Should save to database successfully")
    void testProcessRequest_SavesSuccessfully() {
        // Given
        String input = "flight";
        when(searchHistoryRepository.save(any(SearchHistoryEntity.class)))
                .thenReturn(searchHistory1);

        // When
        FlightFinderResponse response = flightFinderService.processRequest(input);

        // Then
        assertNotNull(response);
        verify(searchHistoryRepository, times(1)).save(any(SearchHistoryEntity.class));
    }

    // ========== getSearchHistory Tests ==========

    @Test
    @DisplayName("Should return all search history ordered by time")
    void testGetSearchHistory_ReturnsAll() {
        // Given
        List<SearchHistoryEntity> histories = Arrays.asList(searchHistory1, searchHistory2);
        when(searchHistoryRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(histories);

        // When
        List<SearchHistoryResponse> result = flightFinderService.getSearchHistory();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("flight", result.get(0).getInputString());
        assertEquals("flightflight", result.get(1).getInputString());

        verify(searchHistoryRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("Should return empty list when no history")
    void testGetSearchHistory_EmptyList() {
        // Given
        when(searchHistoryRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(Arrays.asList());

        // When
        List<SearchHistoryResponse> result = flightFinderService.getSearchHistory();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should correctly convert entity to DTO")
    void testGetSearchHistory_CorrectConversion() {
        // Given
        when(searchHistoryRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(Arrays.asList(searchHistory1));

        // When
        List<SearchHistoryResponse> result = flightFinderService.getSearchHistory();

        // Then
        SearchHistoryResponse response = result.get(0);
        assertEquals(searchHistory1.getId(), response.getId());
        assertEquals(searchHistory1.getInputString(), response.getInputString());
        assertEquals(searchHistory1.getResultCount(), response.getResultCount());
        assertEquals(searchHistory1.getCreatedAt(), response.getCreatedAt());
    }
}
