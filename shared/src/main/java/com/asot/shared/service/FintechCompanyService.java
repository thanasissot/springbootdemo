package com.asot.shared.service;

import com.asot.shared.model.FintechCompany;
import com.asot.shared.repository.FintechCompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FintechCompanyService {
    private final FintechCompanyRepository repository;

    public FintechCompanyService(FintechCompanyRepository repository) {
        this.repository = repository;
    }

    public FintechCompany save(FintechCompany company) {
        return repository.save(company);
    }

    @Transactional(readOnly = true)
    public Optional<FintechCompany> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<FintechCompany> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
