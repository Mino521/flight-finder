package com.flightfinder.repository;

import com.flightfinder.entity.SearchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, Long> {
    /**
     * Find all search history ordered by creation time descending
     */
    List<SearchHistoryEntity> findAllByOrderByCreatedAtDesc();
}
