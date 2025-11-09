package com.asot.shared.service;

import com.asot.shared.model.TradeOrder;
import com.asot.shared.repository.OrderRepository;
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

    public TradeOrder save(TradeOrder tradeOrder) {
        return repository.save(tradeOrder);
    }

    @Transactional(readOnly = true)
    public Optional<TradeOrder> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<TradeOrder> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
