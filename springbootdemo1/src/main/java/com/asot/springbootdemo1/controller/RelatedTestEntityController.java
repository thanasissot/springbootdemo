package com.asot.springbootdemo1.controller;

import com.asot.springbootdemo1.dto.RelatedTestEntityDTO;
import com.asot.springbootdemo1.dto.RelatedTestEntityWithEntityDetailsDTO;
import com.asot.springbootdemo1.service.RelatedTestEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/related-entities")
@RequiredArgsConstructor
@Log4j2
public class RelatedTestEntityController {

    private final RelatedTestEntityService relatedTestEntityService;

    @GetMapping
    public List<RelatedTestEntityDTO> getAllRelatedEntities() {
        log.info("getAllRelatedEntities request received");
        return relatedTestEntityService.getAllRelatedEntities();
    }

    @GetMapping("/details")
    public List<RelatedTestEntityWithEntityDetailsDTO> getAllRelatedEntitiesWithTestDetails() {
        log.info("getAllRelatedEntities request received");
        return relatedTestEntityService.getAllRelatedEntitiesWithEntityDetails();
    }

    @GetMapping("/paginated")
    public Page<RelatedTestEntityDTO> getAllRelatedEntitiesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return relatedTestEntityService.getAllRelatedEntitiesPaginated(PageRequest.of(page, size));
    }

    @GetMapping("/by-test-entity/{testEntityId}")
    public List<RelatedTestEntityDTO> getByTestEntityId(@PathVariable Long testEntityId) {
        log.info("getByTestEntityId request received for ID: {}", testEntityId);
        return relatedTestEntityService.findByTestEntityId(testEntityId);
    }

    @PostMapping
    public RelatedTestEntityDTO createRelatedEntity(@RequestBody @Valid RelatedTestEntityDTO dto) {
        log.info("createRelatedEntity request received");
        return relatedTestEntityService.saveRelatedEntity(dto);
    }
}
