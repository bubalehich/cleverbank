package com.vlasova.cleverbank.servlet.dto;

import lombok.Data;

@Data
public class WithdrawalsRequestDto {
    private String bankNumber;
    private String accountNumber;
    private String amount;
    private String currency;
    private String payload;
}
