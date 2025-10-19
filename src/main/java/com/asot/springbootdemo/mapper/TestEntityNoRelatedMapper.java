package com.asot.springbootdemo.mapper;

import com.asot.springbootdemo.dto.TestEntityDTO;
import com.asot.springbootdemo.dto.TestEntityNoRelatedDTO;
import com.asot.springbootdemo.model.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestEntityNoRelatedMapper {

    TestEntityNoRelatedDTO toDTO(TestEntity testEntity);

    TestEntity toEntity(TestEntityNoRelatedDTO testEntityDTO);

}
