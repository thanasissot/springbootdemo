package com.asot.shared.repository;

import com.asot.shared.model.FintechCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FintechCompanyRepository extends JpaRepository<FintechCompany, Long> {
}
