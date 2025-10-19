package com.asot.springbootdemo.repository;

import com.asot.springbootdemo.model.RelatedTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelatedTestEntityRepository
        extends JpaRepository<RelatedTestEntity, Long>, JpaSpecificationExecutor<RelatedTestEntity> {

}
