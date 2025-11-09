package com.asot.springbootdemo1.service;

import com.asot.springbootdemo1.dto.RelatedTestEntityDTO;
import com.asot.springbootdemo1.dto.RelatedTestEntityWithEntityDetailsDTO;
import com.asot.springbootdemo1.mapper.RelatedTestEntityMapper;
import com.asot.springbootdemo1.mapper.RelatedTestEntityWithEntityDetailsMapper;
import com.asot.springbootdemo1.model.RelatedTestEntity;
import com.asot.springbootdemo1.repository.RelatedTestEntityRepository;
import com.asot.springbootdemo1.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatedTestEntityService {
    private final RelatedTestEntityRepository relatedTestEntityRepository;
    private final TestEntityRepository testEntityRepository;
    private final RelatedTestEntityMapper relatedTestEntityMapper;
    private final RelatedTestEntityWithEntityDetailsMapper relatedTestEntityWithEntityDetailsMapper;

    public List<RelatedTestEntityDTO> getAllRelatedEntities() {
        return relatedTestEntityRepository.findAll()
                .stream().map(relatedTestEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RelatedTestEntityWithEntityDetailsDTO> getAllRelatedEntitiesWithEntityDetails() {
        return relatedTestEntityRepository.findAll()
                .stream().map(relatedTestEntityWithEntityDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<RelatedTestEntityDTO> getAllRelatedEntitiesPaginated(Pageable pageable) {
        Page<RelatedTestEntity> entities = relatedTestEntityRepository.findAll(pageable);
        List<RelatedTestEntityDTO> dtoList = entities.getContent()
                .stream()
                .map(relatedTestEntityMapper::toDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, entities.getTotalElements());
    }

    public RelatedTestEntityDTO saveRelatedEntity(RelatedTestEntityDTO dto) {
        RelatedTestEntity entity = relatedTestEntityMapper.toEntity(dto);

        // Set the proper TestEntity reference
        if (dto.getTestEntityId() != null) {
            testEntityRepository.findById(dto.getTestEntityId())
                    .ifPresent(entity::setTestEntity);
        }

        return relatedTestEntityMapper.toDTO(
                relatedTestEntityRepository.save(entity));
    }

    public List<RelatedTestEntityDTO> findByTestEntityId(Long testEntityId) {
        return relatedTestEntityRepository.findAll((root, query, cb) ->
                        cb.equal(root.get("testEntity").get("id"), testEntityId))
                .stream()
                .map(relatedTestEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

}
