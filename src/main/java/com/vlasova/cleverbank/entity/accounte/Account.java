package com.vlasova.cleverbank.entity.accounte;

import com.vlasova.cleverbank.entity.Bank;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return new EqualsBuilder().append(id, account.id).append(isActive, account.isActive).append(customer, account.customer).append(number, account.number).append(openingDate, account.openingDate).append(bank, account.bank).append(balance, account.balance).append(type, account.type).append(currency, account.currency).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(customer)
                .append(number)
                .append(openingDate)
                .append(bank)
                .append(balance)
                .append(type)
                .append(currency)
                .append(isActive)
                .toHashCode();
    }
}
