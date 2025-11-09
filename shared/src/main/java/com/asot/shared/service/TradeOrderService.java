package com.asot.shared.service;

import com.asot.shared.model.TradeOrder;
import com.asot.shared.repository.TradeOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TradeOrderService {
    private final TradeOrderRepository repository;

    public TradeOrderService(TradeOrderRepository repository) {
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
