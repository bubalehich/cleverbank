package com.vlasova.cleverbank.validator;

import com.vlasova.cleverbank.exception.ValidationException;
import com.vlasova.cleverbank.servlet.dto.WithdrawalsRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawalsRequestValidator {
    public static void validate(WithdrawalsRequestDto requestDto) {
        checkForNull(requestDto);
        try {
            var amount = BigDecimal.valueOf(Double.parseDouble(requestDto.getAmount()));
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Amount should be greater than zero.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format of amount");
        }
        try {
            Long.parseLong(requestDto.getAccountNumber());
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format of account number");
        }

        try {
            Long.parseLong(requestDto.getAccountNumber());
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format of account number");
        }
    }

    private static void checkForNull(WithdrawalsRequestDto requestDto) {
        if (StringUtils.isEmpty(requestDto.getAccountNumber())) {
            throw new ValidationException("Account number should not be empty");
        }
        if (StringUtils.isEmpty(requestDto.getBankNumber())) {
            throw new ValidationException("Bank number should not be empty");
        }
        if (StringUtils.isEmpty(requestDto.getCurrency())) {
            throw new ValidationException("Currency number should not be empty");
        }
        if (StringUtils.isEmpty(requestDto.getAmount())) {
            throw new ValidationException("Amount number should not be empty");
        }
    }
}
