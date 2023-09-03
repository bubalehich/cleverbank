package com.vlasova.cleverbank.validator;

import com.vlasova.cleverbank.exception.ValidationException;
import com.vlasova.cleverbank.servlet.dto.RefillRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefillRequestValidator {
    public static void validate(RefillRequestDto dto) {
        try {
            var amount = BigDecimal.valueOf(Double.parseDouble(dto.getAmount()));
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Amount should be greater than zero.");
            }
        } catch (NullPointerException | NumberFormatException e) {
            throw new ValidationException("Invalid format of amount");
        }
        try {
            Long.parseLong(dto.getAccountNumber());
        } catch (NullPointerException | NumberFormatException e) {
            throw new ValidationException("Invalid format of account number");
        }

        try {
            Long.parseLong(dto.getAccountNumber());
        } catch (NullPointerException | NumberFormatException e) {
            throw new ValidationException("Invalid format of account number");
        }
    }
}
