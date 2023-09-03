package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.entity.transaction.Transaction;
import com.vlasova.cleverbank.exception.TransactionExecutionException;
import com.vlasova.cleverbank.servlet.dto.WithdrawalsRequestDto;
import com.vlasova.cleverbank.servlet.dto.WithdrawalsResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class WithdrawalsService {
    private static final String OPERATION_TYPE = "Withdrawals";
    private final BankService bankService;
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;
    private final TransactionTypeService transactionTypeService;

    public WithdrawalsResponse doWithdrawalsOperation(WithdrawalsRequestDto withdrawalsRequest) {
        var account = accountService.getById(Long.parseLong(withdrawalsRequest.getAccountNumber()));
        var bank = bankService.getById(Long.parseLong(withdrawalsRequest.getBankNumber()));
        var currency = currencyService.getById(Long.parseLong(withdrawalsRequest.getCurrency()));
        var transactionType = transactionTypeService.getByName(OPERATION_TYPE);
        var amount = BigDecimal.valueOf(Double.parseDouble(withdrawalsRequest.getAmount()));

        Account backup = null;
        boolean isTransactionStarted = false;
        try {
            if (account.isActive()) {
                backup = account.copy();

                account.setBalance(account.getBalance().add(amount));
                Transaction transaction = new Transaction();
                transaction.setDate(Instant.now());
                transaction.setReceiver(account);
                transaction.setTransactionType(transactionType);
                transaction.setAmount(amount);
                transaction.setNumber(UUID.randomUUID());
                transaction.setPayload(withdrawalsRequest.getPayload());
                transaction.setCurrency(currency);
                transaction.setSender(account);

                accountService.save(account);
                transactionService.save(transaction);

                return new WithdrawalsResponse(
                        transaction.getNumber().toString(),
                        transaction.getAmount(),
                        transaction.getCurrency().getHandle(),
                        bank.getName(),
                        account.getNumber(),
                        transaction.getDate().toString(),
                        transaction.getPayload(),
                        account.getBalance().toString()
                );
            }
            throw new TransactionExecutionException("The account is not available. Try later or call the helpdesk.");
        } catch (Exception e) {
            if (isTransactionStarted && backup != null) {
                accountService.save(backup);
            }
            throw new TransactionExecutionException(e);
        }
    }
}
