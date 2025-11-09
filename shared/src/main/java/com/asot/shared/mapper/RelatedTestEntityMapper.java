package com.asot.shared.mapper;

import com.asot.shared.dto.RelatedTestEntityDTO;
import com.asot.shared.model.RelatedTestEntity;
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
