package com.vlasova.cleverbank.entity;

import com.vlasova.cleverbank.entity.accounte.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bank {
    private long id;

    private String bicNumber;

    private String name;

    private String country;

    private List<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return new EqualsBuilder().append(id, bank.id).append(bicNumber, bank.bicNumber).append(name, bank.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(bicNumber)
                .append(name)
                .toHashCode();
    }
}
