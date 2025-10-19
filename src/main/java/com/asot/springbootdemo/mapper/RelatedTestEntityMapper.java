package com.asot.springbootdemo.mapper;

import com.asot.springbootdemo.dto.RelatedTestEntityDTO;
import com.asot.springbootdemo.model.RelatedTestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
//@Mapper(componentModel = "spring", uses = {TestEntityMapper.class})
public interface RelatedTestEntityMapper {

    @Mapping(source = "testEntity.id", target = "testEntityId")
    RelatedTestEntityDTO toDTO(RelatedTestEntity relatedTestEntity);

    @Mapping(source = "testEntityId", target = "testEntity.id")
    RelatedTestEntity toEntity(RelatedTestEntityDTO relatedTestEntityDTO);
}
