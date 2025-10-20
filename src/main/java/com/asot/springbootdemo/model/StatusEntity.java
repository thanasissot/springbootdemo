package com.asot.springbootdemo.model;

import com.asot.springbootdemo.state.machine.enums.States;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdTs;
    private LocalDateTime lastUpdatedTs;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'INITIAL'")
    private States currentState;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NAN'")
    private States stateA;
    private LocalDateTime passedStateATs;
    private LocalDateTime latestStateAErrorTs;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NAN'")
    private States stateB;
    private LocalDateTime passedStateBTs;
    private LocalDateTime latestStateBErrorTs;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NAN'")
    private States stateC;
    private LocalDateTime passedStateCTs;
    private LocalDateTime latestStateCErrorTs;
}
