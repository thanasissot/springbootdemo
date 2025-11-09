package com.asot.springbootdemo1.service;

import com.asot.springbootdemo1.model.Order;
import com.asot.springbootdemo1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
