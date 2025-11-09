package com.asot.shared.service;

import com.asot.shared.dto.NewTradeOrder;
import com.asot.shared.model.FintechCompany;
import com.asot.shared.model.Salesman;
import com.asot.shared.model.TradeOrder;
import com.asot.shared.model.TradeOrderType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
@Transactional
public class TradeOrderConsumerService {

    private final TradeOrderService tradeOrderService;
    private final FintechCompanyService fintechCompanyService;
    private final SalesmanService salesmanService;
    private final Validator validator;

    public TradeOrderConsumerService(TradeOrderService tradeOrderService,
                                     FintechCompanyService fintechCompanyService,
                                     SalesmanService salesmanService,
                                     Validator validator) {
        this.tradeOrderService = tradeOrderService;
        this.fintechCompanyService = fintechCompanyService;
        this.salesmanService = salesmanService;
        this.validator = validator;
    }

    public TradeOrder processNewTradeOrder(NewTradeOrder dto) {
        log.info("Processing new trade order: {}", dto);

        // Validate DTO
        validateDto(dto);

        // Validate business rules
        validateBusinessRules(dto);

        // Fetch entities
        FintechCompany fromCompany = fintechCompanyService.findById(dto.getFromFintechCompany())
                .orElseThrow(() -> new IllegalArgumentException("From fintech company not found: " + dto.getFromFintechCompany()));

        FintechCompany toCompany = fintechCompanyService.findById(dto.getToFintechCompany())
                .orElseThrow(() -> new IllegalArgumentException("To fintech company not found: " + dto.getToFintechCompany()));

        Salesman fromSalesman = salesmanService.findById(dto.getFromSalesman())
                .orElseThrow(() -> new IllegalArgumentException("From salesman not found: " + dto.getFromSalesman()));


        // Create and save trade order
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setType(TradeOrderType.valueOf(dto.getType()));
        tradeOrder.setFromFintechCompany(fromCompany);
        tradeOrder.setToFintechCompany(toCompany);
        tradeOrder.setFromSalesman(fromSalesman);
        tradeOrder.setAmount(dto.getAmount());

        TradeOrder savedOrder = tradeOrderService.save(tradeOrder);
        log.info("Successfully created trade order with dealRef: {}", savedOrder.getDealRef());

        return savedOrder;
    }

    private void validateDto(NewTradeOrder dto) {
        Set<ConstraintViolation<NewTradeOrder>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<NewTradeOrder> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private void validateBusinessRules(NewTradeOrder dto) {
        if (dto.getFromFintechCompany().equals(dto.getToFintechCompany())) {
            throw new IllegalArgumentException("From and To fintech companies must be different");
        }

        if (!"SUB".equals(dto.getType()) && !"RED".equals(dto.getType())) {
            throw new IllegalArgumentException("Trade order type must be SUB or RED");
        }
    }
}

