package com.asot.shared.mapper;

import com.asot.shared.dto.TestEntityDTO;
import com.asot.shared.model.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RelatedTestEntityMapper.class})
public interface TestEntityMapper {

    TestEntityDTO toDTO(TestEntity testEntity);

    TestEntity toEntity(TestEntityDTO testEntityDTO);

}
