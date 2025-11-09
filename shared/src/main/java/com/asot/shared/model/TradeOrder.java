package com.asot.shared.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // exclude field-based hashCode
public class TradeOrder implements Serializable {
    @Id
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    private Long orderId;

    @Column(unique = true, nullable = false)
    private String dealRef; // e.g. 2025110900001A

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "salesman_id", nullable = false)
    private Salesman salesman;

    private LocalDateTime createdTs;
    private String createdBy;
    private LocalDateTime lastUpdatedTs;
    private String lastUpdatedBy;

    // JVM-local daily counters; for DB-accurate counts use DB-backed approach
    private static final Map<String, AtomicInteger> DAILY_COUNTERS = new ConcurrentHashMap<>();
    private static final Random RAND = new Random();
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @PrePersist
    protected void onCreate() {
        if (createdTs == null) createdTs = LocalDateTime.now();
        if (createdBy == null) createdBy = "system";
        lastUpdatedTs = createdTs;
        lastUpdatedBy = createdBy;

        if (dealRef == null || dealRef.isBlank()) {
            String datePart = LocalDate.now().format(DATE_FMT);
            int count = DAILY_COUNTERS.computeIfAbsent(datePart, d -> new AtomicInteger(0)).incrementAndGet();
            String numPart = String.format("%05d", count);
            char letter = (char) ('A' + RAND.nextInt(5)); // A-E
            dealRef = datePart + numPart + letter;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedTs = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "OrderEntity{orderId=" + orderId + ", dealRef='" + dealRef + '\'' + '}';
    }
}
