package com.asot.springbootdemo1.repository;

import com.asot.springbootdemo1.model.CachedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CachedEntityRepository
        extends JpaRepository<CachedEntity, Long>, JpaSpecificationExecutor<CachedEntity> {
}
