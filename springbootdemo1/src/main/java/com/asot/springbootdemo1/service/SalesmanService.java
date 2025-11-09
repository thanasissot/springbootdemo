package com.asot.springbootdemo1.service;

import com.asot.springbootdemo1.model.Salesman;
import com.asot.springbootdemo1.repository.SalesmanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesmanService {
    private final SalesmanRepository repository;

    public SalesmanService(SalesmanRepository repository) {
        this.repository = repository;
    }

    public Salesman save(Salesman salesman) {
        return repository.save(salesman);
    }

    @Transactional(readOnly = true)
    public Optional<Salesman> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Salesman> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
