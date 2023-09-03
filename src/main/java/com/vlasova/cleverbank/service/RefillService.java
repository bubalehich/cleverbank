package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.entity.transaction.Transaction;
import com.vlasova.cleverbank.exception.TransactionExecutionException;
import com.vlasova.cleverbank.servlet.dto.RefillRequestDto;
import com.vlasova.cleverbank.servlet.dto.RefillResponse;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
public class RefillService {
    private final BankService bankService;
    private final AccountService accountService;
    private final CurrencyService currencyService;

    private final TransactionTypeService transactionTypeService;

    public RefillResponse doRefillOperation(RefillRequestDto refillRequest) {
        var bank = bankService.getById(Long.parseLong(refillRequest.getBankNumber()));
        var account = accountService.getById(Long.parseLong(refillRequest.getAccountNumber()));
        var currency = currencyService.getById(Long.parseLong(refillRequest.getCurrency()));
        var amount = BigDecimal.valueOf(Double.parseDouble(refillRequest.getAmount()));
        var transactionType = transactionTypeService.getByName("Refill");

        RefillResponse response = new RefillResponse();
        Account backup = null;
        boolean isTransactionStarted = false;
        try {
            if (account.getCurrency().equals(currency) && lockAccountForOperation(account.getId())) {
                backup = account.copy();
                isTransactionStarted = true;

                if (account.getBalance().compareTo(amount) <= 0) {
                    throw new TransactionExecutionException("Not enough money, my lord!:<");
                }
                account.setBalance(account.getBalance().subtract(amount));
                Transaction transaction = new Transaction();
                transaction.setAmount(amount);
                transaction.setDate(Instant.now());
                transaction.setReceiver(account);
                transaction.setTransactionType(transactionType);

                response = new RefillResponse(
                        transaction.getNumber().toString(),
                        transaction.getAmount(),
                        transaction.getCurrency().getHandle(),
                        transaction.getReceiver().getBank().getName(),
                        transaction.getReceiver().getNumber(),
                        transaction.getDate().toString()
                );
            }
        } catch (Exception e) {
            if (isTransactionStarted && backup != null) {
                accountService.save(backup);
            }
            throw new TransactionExecutionException(e);
        } finally {
            unlockAccountAfterOperation(account.getId());
        }

        return response;
    }


    private boolean lockAccountForOperation(Long accountNumber) {
        try {
            var account = accountService.getById(accountNumber);
            if (account.isLocked()) {
                return false;
            }
            account.setLocked(true);
            accountService.save(account);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean unlockAccountAfterOperation(Long accountNumber) {
        try {
            var account = accountService.getById(accountNumber);
            if (!account.isLocked()) {
                throw new TransactionExecutionException("Account unlocked for some reasons!");
            }
            account.setLocked(false);
            accountService.save(account);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
