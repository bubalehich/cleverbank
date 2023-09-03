package com.vlasova.cleverbank.servlet.dto;

import lombok.Data;

@Data
public class TransferRequestDto {
    private String bankReceiver;
    private String accountReceiver;

    private String bankSender;
    private String accountSender;

    private String amount;
    private String currency;

    private String payload;
}
