package com.asot.springbootdemo.service;

import com.asot.springbootdemo.dto.TestEntityDTO;
import com.asot.springbootdemo.mapper.TestEntityMapper;
import com.asot.springbootdemo.model.TestEntity;
import com.asot.springbootdemo.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
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

    public TestEntityDTO saveTestEntity(TestEntityDTO testEntityDTO) {
        return testEntityMapper.toDTO(
                testEntityRepository.save(testEntityMapper.toEntity(testEntityDTO)));
    }


}
