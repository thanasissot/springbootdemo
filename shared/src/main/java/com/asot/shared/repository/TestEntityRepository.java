package com.asot.shared.repository;

import com.asot.shared.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TestEntityRepository
        extends JpaRepository<TestEntity, Long>, JpaSpecificationExecutor<TestEntity> {

    @Query("SELECT MAX(t.id) FROM TestEntity t")
    Long findMaxId();

}
