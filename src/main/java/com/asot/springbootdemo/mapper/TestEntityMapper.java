package com.asot.springbootdemo.mapper;

import com.asot.springbootdemo.dto.TestEntityDTO;
import com.asot.springbootdemo.model.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RelatedTestEntityMapper.class})
public interface TestEntityMapper {

    TestEntityDTO toDTO(TestEntity testEntity);

    TestEntity toEntity(TestEntityDTO testEntityDTO);

}
