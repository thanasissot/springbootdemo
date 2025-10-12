package com.asot.springbootdemo.controller;

import com.asot.springbootdemo.model.TestEntity;
import com.asot.springbootdemo.service.TestEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-entities")
@RequiredArgsConstructor
public class TestEntityController {
    private final TestEntityService testEntityService;

    @GetMapping
    public List<TestEntity> getAllTestEntities() {
        return testEntityService.getAllTestEntities();
    }

    @PostMapping
    public TestEntity createTestEntity(@RequestBody TestEntity testEntity) {
        return testEntityService.saveTestEntity(testEntity);
    }

}
