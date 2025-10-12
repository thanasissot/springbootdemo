package com.asot.springbootdemo.controller;

import com.asot.springbootdemo.dto.TestEntityDTO;
import com.asot.springbootdemo.model.TestEntity;
import com.asot.springbootdemo.service.TestEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-entities")
@RequiredArgsConstructor
public class TestEntityController {
    private final TestEntityService testEntityService;

    @GetMapping
    public List<TestEntityDTO> getAllTestEntities() {
        return testEntityService.getAllTestEntities();
    }

    @PostMapping
    public TestEntityDTO createTestEntity(@RequestBody @Valid TestEntityDTO testEntityDTO) {
        return testEntityService.saveTestEntity(testEntityDTO);
    }

}
