package com.asot.springbootdemo1.mapper;

import com.asot.springbootdemo1.dto.TestEntityDTO;
import com.asot.springbootdemo1.model.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RelatedTestEntityMapper.class})
public interface TestEntityMapper {

    TestEntityDTO toDTO(TestEntity testEntity);

    TestEntity toEntity(TestEntityDTO testEntityDTO);

}
