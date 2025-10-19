package com.asot.springbootdemo.controller;

import com.asot.springbootdemo.model.CachedEntity;
import com.asot.springbootdemo.service.CashedEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cached-entities")
@RequiredArgsConstructor
@Log4j2
public class CachedEntityController {

    private final CashedEntityService service;

    @GetMapping
    @Cacheable("cachedEntities")
    public List<CachedEntity> getAllCachedEntities() {
        log.info("CachedEntityController endpoint getAllCachedEntities called");
        return service.getAllCachedEntities();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Caching(
            evict = @CacheEvict(value = "cachedEntities", allEntries = true),
            put = @CachePut(value = "cachedEntity", key = "#result.id")
    )
    public CachedEntity saveCachedEntity(@RequestBody CachedEntity entity) {
        return service.saveCachedEntity(entity);
    }

    @PutMapping("/{id}")
    @Caching(
            evict = @CacheEvict(value = "cachedEntities", allEntries = true),
            put = @CachePut(value = "cachedEntity", key = "#result.id")
    )
    public CachedEntity updateCachedEntity(@PathVariable Long id, @RequestBody CachedEntity updated) {
        return service.updateCachedEntity(id, updated);
    }
}
