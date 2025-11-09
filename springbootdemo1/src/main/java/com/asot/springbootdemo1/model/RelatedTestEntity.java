package com.asot.springbootdemo1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RelatedTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relatedField;

    @ManyToOne
    @JoinColumn(name = "testEntity_id")
    private TestEntity testEntity;

}
