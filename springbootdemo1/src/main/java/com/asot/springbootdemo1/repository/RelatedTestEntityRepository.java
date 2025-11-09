package com.asot.springbootdemo1.repository;

import com.asot.springbootdemo1.model.RelatedTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelatedTestEntityRepository
        extends JpaRepository<RelatedTestEntity, Long>, JpaSpecificationExecutor<RelatedTestEntity> {

}
