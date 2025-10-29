package com.flightfinder.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Data
public class SearchHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_string", nullable = false, length = 100)
    private String inputString;

    @Column(name = "result_count", nullable = false)
    private Integer resultCount;

    @CreationTimestamp  // Hibernate 自动设置创建时间 | Hibernate auto-sets creation time
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
