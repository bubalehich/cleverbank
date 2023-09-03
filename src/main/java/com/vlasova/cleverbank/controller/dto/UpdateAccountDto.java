package com.vlasova.cleverbank.controller.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAccountDto {
    @Positive
    private BigDecimal balance;
}
