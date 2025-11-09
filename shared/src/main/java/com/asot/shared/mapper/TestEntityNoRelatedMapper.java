package com.asot.shared.mapper;

import com.asot.shared.dto.TestEntityNoRelatedDTO;
import com.asot.shared.model.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestEntityNoRelatedMapper {

    TestEntityNoRelatedDTO toDTO(TestEntity testEntity);

    TestEntity toEntity(TestEntityNoRelatedDTO testEntityDTO);

}
