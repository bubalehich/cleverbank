package com.vlasova.cleverbank.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDto {
    @Positive
    private BigDecimal balance;
    @NotBlank
    private Long bankId;
    @NotBlank
    private Long currencyId;
    @NotBlank
    private Long type;
    @NotBlank
    private Long customer;
}
