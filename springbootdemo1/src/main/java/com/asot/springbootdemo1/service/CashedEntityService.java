package com.asot.springbootdemo1.service;

import com.asot.springbootdemo1.model.CachedEntity;
import com.asot.springbootdemo1.repository.CachedEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CashedEntityService {

    private final CachedEntityRepository repository;


    public List<CachedEntity> getAllCachedEntities() {
        log.info("getAllCachedEntities called.");
        return repository.findAll();
    }

    public CachedEntity saveCachedEntity(CachedEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public CachedEntity updateCachedEntity(Long id, CachedEntity updated) {
        return repository.findById(id)
                .map(existing -> {
                    BeanUtils.copyProperties(updated, existing, "id");
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("CachedEntity not found: " + id));
    }
}
