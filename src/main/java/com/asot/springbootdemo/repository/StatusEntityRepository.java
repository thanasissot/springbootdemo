package com.asot.springbootdemo.repository;

import com.asot.springbootdemo.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusEntityRepository extends JpaRepository<StatusEntity, Long> {
}
