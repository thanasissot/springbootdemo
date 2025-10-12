package com.asot.springbootdemo.repository;

import com.asot.springbootdemo.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestEntityRepository
        extends JpaRepository<TestEntity, Long>, JpaSpecificationExecutor<TestEntity> {

}
