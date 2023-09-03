package com.vlasova.cleverbank.servlet.dto;

import lombok.Data;

@Data
public class RefillRequestDto {
    private String bankNumber;
    private String accountNumber;
    private String amount;
    private String currency;
}
