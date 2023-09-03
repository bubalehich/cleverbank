package com.vlasova.cleverbank.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefillResponse {
    private String transactionNumber;
    private BigDecimal amount;
    private String currency;
    private String bank;
    private String accountNumber;
    private String date;
}
