package com.asot.springbootdemo.mapper;

import com.asot.springbootdemo.dto.RelatedTestEntityWithEntityDetailsDTO;
import com.asot.springbootdemo.model.RelatedTestEntity;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
@Mapper(componentModel = "spring", uses = {TestEntityNoRelatedMapper.class})
public interface RelatedTestEntityWithEntityDetailsMapper {

//    @Mapping(source = "testEntity.id", target = "testEntityId")
    RelatedTestEntityWithEntityDetailsDTO toDTO(RelatedTestEntity relatedTestEntity);

//    @Mapping(source = "testEntityId", target = "testEntity.id")
    RelatedTestEntity toEntity(RelatedTestEntityWithEntityDetailsDTO relatedTestEntityDTO);
}
