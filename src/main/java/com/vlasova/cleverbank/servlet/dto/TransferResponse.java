package com.vlasova.cleverbank.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private String transactionNumber;
    private String bankReceiver;
    private String accountReceiver;

    private String bankSender;
    private String accountSender;

    private String amount;
    private String currency;

    private String payload;

    private String date;
}
