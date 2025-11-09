package com.asot.springbootdemo1.mapper;

import com.asot.springbootdemo1.dto.TestEntityNoRelatedDTO;
import com.asot.springbootdemo1.model.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestEntityNoRelatedMapper {

    TestEntityNoRelatedDTO toDTO(TestEntity testEntity);

    TestEntity toEntity(TestEntityNoRelatedDTO testEntityDTO);

}
