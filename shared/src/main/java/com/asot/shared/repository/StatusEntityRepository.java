package com.asot.shared.repository;

import com.asot.shared.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusEntityRepository extends JpaRepository<StatusEntity, Long> {
}
