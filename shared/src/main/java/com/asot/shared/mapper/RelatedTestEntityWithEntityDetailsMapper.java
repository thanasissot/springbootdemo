package com.asot.shared.mapper;

import com.asot.shared.dto.RelatedTestEntityWithEntityDetailsDTO;
import com.asot.shared.model.RelatedTestEntity;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
@Mapper(componentModel = "spring", uses = {TestEntityNoRelatedMapper.class})
public interface RelatedTestEntityWithEntityDetailsMapper {

//    @Mapping(source = "testEntity.id", target = "testEntityId")
    RelatedTestEntityWithEntityDetailsDTO toDTO(RelatedTestEntity relatedTestEntity);

//    @Mapping(source = "testEntityId", target = "testEntity.id")
    RelatedTestEntity toEntity(RelatedTestEntityWithEntityDetailsDTO relatedTestEntityDTO);
}
