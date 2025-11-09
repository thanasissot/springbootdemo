package com.asot.shared.repository;

import com.asot.shared.model.CachedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CachedEntityRepository
        extends JpaRepository<CachedEntity, Long>, JpaSpecificationExecutor<CachedEntity> {
}
