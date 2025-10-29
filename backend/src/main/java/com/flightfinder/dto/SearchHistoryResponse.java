package com.flightfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SearchHistoryResponse {
    private Long id;
    private String inputString;
    private Integer resultCount;
    private LocalDateTime createdAt;
}
