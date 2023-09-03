package com.vlasova.cleverbank.validator;

import com.vlasova.cleverbank.exception.ValidationException;
import com.vlasova.cleverbank.servlet.dto.TransferRequestDto;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class TransferRequestValidator {
    public static void validate(TransferRequestDto dto) {
        checkForNull(dto);

        try {
            var amount = BigDecimal.valueOf(Double.parseDouble(dto.getAmount()));
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Amount should be greater than zero.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format of amount");
        }
        checkForParse(dto.getBankSender());
        checkForParse(dto.getBankReceiver());
        checkForParse(dto.getAccountSender());
        checkForParse(dto.getAccountReceiver());
    }

    private static void checkForParse(String number) {
        try {
            Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format of account number");
        }
    }

    private static void checkForNull(TransferRequestDto dto) {
        if (StringUtils.isEmpty(dto.getBankReceiver())) {
            throw new ValidationException("Bank receiver number should not be empty");
        }
        if (StringUtils.isEmpty(dto.getAccountReceiver())) {
            throw new ValidationException("Account receiver number should not be empty");
        }
        if (StringUtils.isEmpty(dto.getBankSender())) {
            throw new ValidationException("Bank sender number should not be empty");
        }
        if (StringUtils.isEmpty(dto.getAccountSender())) {
            throw new ValidationException("Account sender number should not be empty");
        }
        if (StringUtils.isEmpty(dto.getAmount())) {
            throw new ValidationException("Amount should not be empty");
        }
        if (StringUtils.isEmpty(dto.getCurrency())) {
            throw new ValidationException("Currency should not be empty");
        }
    }
}
