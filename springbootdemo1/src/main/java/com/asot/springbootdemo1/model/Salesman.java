package com.asot.springbootdemo1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // exclude field-based hashCode
public class Salesman implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fintech_company_id", nullable = false)
    private FintechCompany fintechCompany;

    private LocalDateTime createdTs;
    private String createdBy;
    private LocalDateTime lastUpdatedTs;
    private String lastUpdatedBy;

    @PrePersist
    protected void onCreate() {
        if (createdTs == null) createdTs = LocalDateTime.now();
        if (createdBy == null) createdBy = "system";
        lastUpdatedTs = createdTs;
        lastUpdatedBy = createdBy;
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedTs = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Salesman{id=" + id + ", name='" + firstName + " " + lastName + '\'' + ", email='" + email + '\'' + '}';
    }
}

