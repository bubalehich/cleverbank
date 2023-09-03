package com.vlasova.cleverbank.service.operation;

import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.entity.transaction.Transaction;
import com.vlasova.cleverbank.exception.TransactionExecutionException;
import com.vlasova.cleverbank.service.AccountService;
import com.vlasova.cleverbank.service.BankService;
import com.vlasova.cleverbank.service.CurrencyService;
import com.vlasova.cleverbank.service.TransactionService;
import com.vlasova.cleverbank.service.TransactionTypeService;
import com.vlasova.cleverbank.servlet.dto.TransferRequestDto;
import com.vlasova.cleverbank.servlet.dto.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class TransferService extends AbstractLockAccountService {
    private static final String OPERATION_TYPE = "Transfer";

    private final BankService bankService;
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final TransactionTypeService transactionTypeService;
    private final TransactionService transactionService;

    public TransferResponse doTransferOperation(TransferRequestDto dto) {
        var bankReceiver = bankService.getById(Long.parseLong(dto.getBankReceiver()));
        var bankSender = bankService.getById(Long.parseLong(dto.getBankSender()));
        var accountReceiver = accountService.getById(Long.parseLong(dto.getAccountReceiver()));
        var accountSender = accountService.getById(Long.parseLong(dto.getAccountSender()));
        var currency = currencyService.getById(Long.parseLong(dto.getCurrency()));
        var amount = BigDecimal.valueOf(Double.parseDouble(dto.getAmount()));
        var transactionType = transactionTypeService.getByName(OPERATION_TYPE);

        Account receiverAccountBackup = null;
        Account senderAccountBackup = null;
        boolean isTransactionStarted = false;

        try {
            if (accountSender.isTransactionCanBeStarted() && accountReceiver.isTransactionCanBeStarted() && accountSender.getCurrency().equals(currency)) {
                receiverAccountBackup = accountReceiver;
                senderAccountBackup = accountSender;

                isTransactionStarted = true;

                if (accountSender.getBalance().compareTo(amount) <= 0) {
                    throw new TransactionExecutionException("Not enough money, my lord!:<");
                }

                accountSender.withdrawMoney(amount);
                accountReceiver.addMoney(amount);

                accountService.save(accountSender);
                accountService.save(accountReceiver);

                var transaction = new Transaction();
                transaction.setTransactionType(transactionType);
                transaction.setAmount(amount);
                transaction.setNumber(UUID.randomUUID());
                transaction.setDate(Instant.now());
                transaction.setReceiver(accountReceiver);
                transaction.setSender(accountSender);
                transaction.setPayload(dto.getPayload());
                transaction.setCurrency(currency);

                transactionService.save(transaction);

                return new TransferResponse(
                        transaction.getNumber().toString(),
                        bankReceiver.getName(),
                        accountReceiver.getNumber(),
                        bankSender.getName(),
                        accountSender.getNumber(),
                        amount.toString(),
                        currency.getHandle(),
                        StringUtils.isEmpty(dto.getPayload()) ? "" : dto.getPayload(),
                        transaction.getDate().toString()
                );
            }
        } catch (Exception e) {
            if (isTransactionStarted && receiverAccountBackup != null && senderAccountBackup != null) {
                accountService.save(receiverAccountBackup);
                accountService.save(senderAccountBackup);
            }
        } finally {
            unlockAccountAfterOperation(accountReceiver.getId());
            unlockAccountAfterOperation(accountSender.getId());
        }
        throw new TransactionExecutionException("The transfer can be done. Try later or call the helpdesk.");
    }
}
