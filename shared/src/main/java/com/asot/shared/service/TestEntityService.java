package com.asot.shared.service;

import com.asot.shared.dto.TestEntityDTO;
import com.asot.shared.mapper.TestEntityMapper;
import com.asot.shared.model.TestEntity;
import com.asot.shared.repository.TestEntityRepository;
import com.asot.shared.specifications.TestEntitySpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestEntityService {

    private final TestEntityRepository testEntityRepository;
    private final TestEntityMapper testEntityMapper;

    public List<TestEntityDTO> getAllTestEntities() {
        return testEntityRepository.findAll()
                .stream().map(testEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TestEntityDTO> getAllTestEntitiesByName(String name) {
        Specification<TestEntity> spec = Specification.unrestricted();
        if (name != null) {
            spec = spec.and(TestEntitySpecifications.nameContains(name));
        }
        return testEntityRepository.findAll(spec)
                .stream().map(testEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<TestEntityDTO> getAllTestEntitiesPaginated(Pageable pageable) {
        Page<TestEntity> testEntities = testEntityRepository.findAll(pageable);
        List<TestEntityDTO> dtoList = testEntities.getContent()
                .stream()
                .map(testEntityMapper::toDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, testEntities.getTotalElements());
    }

    public TestEntityDTO saveTestEntity(TestEntityDTO testEntityDTO) {
        return testEntityMapper.toDTO(
                testEntityRepository.save(testEntityMapper.toEntity(testEntityDTO)));
    }


}
