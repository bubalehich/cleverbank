package com.vlasova.cleverbank.entity.accounte;

import com.vlasova.cleverbank.entity.Bank;
import com.vlasova.cleverbank.entity.Currency;
import com.vlasova.cleverbank.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    private long id;

    private Customer customer;

    private String number;

    private Instant openingDate = Instant.now();

    private Bank bank;

    private BigDecimal balance;

    private AccountType type;

    private Currency currency;

    private boolean isActive;

    private boolean isLocked;

    public Account copy() {
        return new Account(id, customer, number, openingDate, bank, balance, type, currency, isActive, isLocked);
    }

    public boolean isNew() {
        return id == 0;
    }

    public boolean isTransactionCanBeStarted() {
        return isActive && !isLocked;
    }

    public void withdrawMoney(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }

    public void addMoney(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return new EqualsBuilder().append(id, account.id).append(number, account.number).append(openingDate, account.openingDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(number).append(openingDate).toHashCode();
    }
}
