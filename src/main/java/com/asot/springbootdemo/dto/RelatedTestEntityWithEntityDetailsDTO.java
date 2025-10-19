package com.asot.springbootdemo.dto;

import com.asot.springbootdemo.model.TestEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RelatedTestEntityWithEntityDetailsDTO {
    private Long id;

    @NotEmpty
    private String relatedField;

    private TestEntityNoRelatedDTO testEntity;
}
