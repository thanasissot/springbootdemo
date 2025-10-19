package com.asot.springbootdemo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RelatedTestEntityDTO {
    private Long id;

    @NotEmpty
    private String relatedField;

    private Long testEntityId;
}
