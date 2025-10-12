package com.asot.springbootdemo.service;

import com.asot.springbootdemo.model.TestEntity;
import com.asot.springbootdemo.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestEntityService {

    private final TestEntityRepository testEntityRepository;

    public List<TestEntity> getAllTestEntities() {
        return testEntityRepository.findAll();
    }

    public TestEntity saveTestEntity(TestEntity testEntity) {
        return testEntityRepository.save(testEntity);
    }


}
