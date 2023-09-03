package com.vlasova.cleverbank.service.operation;

import com.vlasova.cleverbank.exception.TransactionExecutionException;
import com.vlasova.cleverbank.service.AccountService;

//TODO think about more acceptable name
public abstract class AbstractLockAccountService {
    private AccountService accountService;

    protected boolean lockAccountForOperation(Long accountNumber) {
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

    protected boolean unlockAccountAfterOperation(Long accountNumber) {
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
