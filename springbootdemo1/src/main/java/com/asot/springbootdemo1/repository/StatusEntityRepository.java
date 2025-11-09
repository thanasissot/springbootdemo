package com.asot.springbootdemo1.repository;

import com.asot.springbootdemo1.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusEntityRepository extends JpaRepository<StatusEntity, Long> {
}
