package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.controller.dto.CreateAccountDto;
import com.vlasova.cleverbank.controller.dto.UpdateAccountDto;
import com.vlasova.cleverbank.dao.impl.account.AccountDao;
import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.exception.EntityUpdateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class AccountService {
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final CustomerService customerService;
    private final AccountTypeService accountTypeService;
    private AccountDao accountDao;

    public Account getById(long id) {
        return accountDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", id)));
    }

    public Account save(Account account) {
        if (account.isNew()) {
            return accountDao.save(account);
        }
        accountDao.update(account);
        return accountDao.findById(account.getId()).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", account.getId())));
    }

    public Account create(CreateAccountDto dto) {
        var currency = currencyService.getById(dto.getCurrencyId());
        var bank = bankService.getById(dto.getBankId());
        var customer = customerService.getById(dto.getCustomer());
        var accountType = accountTypeService.getById(dto.getType());

        Account account = new Account();
        account.setBalance(dto.getBalance());
        account.setNumber(UUID.randomUUID().toString());
        account.setCurrency(currency);
        account.setOpeningDate(Instant.now());
        account.setActive(true);
        account.setBank(bank);
        account.setLocked(false);
        account.setType(accountType);
        account.setCustomer(customer);

        return accountDao.save(account);
    }

    public Account update(UpdateAccountDto dto, long id) {
        var account = accountDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", id)));

        if (!account.isTransactionCanBeStarted()) {
            throw new EntityUpdateException(String.format("Account with id %d can't be updated.", id));
        }
        account.setBalance(dto.getBalance());
        accountDao.update(account);

        return accountDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", id)));
    }

    public void delete(long id) {
        var account = accountDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", id)));
        account.setActive(false);
        accountDao.update(account);
    }

    public List<Account> getAllWithPagination(Integer offset, Integer limit) {
        return accountDao.findAll(offset, limit).stream().toList();
    }
}
