package com.asot.shared.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
//@XmlRootElement(name = "NewTradeOrder")
public class NewTradeOrder {
//    @XmlElement(name = "FromFintechCompanyId")
    @NotNull(message = "From fintech company ID cannot be null")
    private Long fromFintechCompany;

//    @XmlElement(name = "ToFintechCompanyId")
    @NotNull(message = "To fintech company ID cannot be null")
    private Long toFintechCompany;

//    @XmlElement(name = "FromSalesman")
    @NotNull(message = "From salesman ID cannot be null")
    private Long fromSalesman;

//    @XmlElement(name = "ToSalesman")
//    @NotNull(message = "To salesman ID cannot be null")
//    private Long toSalesmanId;

//    @XmlElement(name = "Amount")
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

//    @XmlElement(name = "Type")
    @NotNull(message = "Trade order type cannot be null")
    private String type; // SUB or RED
}

