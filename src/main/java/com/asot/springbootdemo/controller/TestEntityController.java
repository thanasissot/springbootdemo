package com.asot.springbootdemo.controller;

import com.asot.springbootdemo.dto.TestEntityDTO;
import com.asot.springbootdemo.model.TestEntity;
import com.asot.springbootdemo.service.TestEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-entities")
@RequiredArgsConstructor
@Log4j2
public class TestEntityController {
    private final TestEntityService testEntityService;

    @GetMapping
    public List<TestEntityDTO> getAllTestEntities() {
        log.info("getAllTestEntities request received.");
        return testEntityService.getAllTestEntities();
    }

    @GetMapping("/paginated")
    public Page<TestEntityDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return testEntityService.getAllTestEntitiesPaginated(PageRequest.of(page, size));
    }

    @PostMapping
    public TestEntityDTO createTestEntity(@RequestBody @Valid TestEntityDTO testEntityDTO) {
        log.info("createTestEntity request received.");
        return testEntityService.saveTestEntity(testEntityDTO);
    }

}
