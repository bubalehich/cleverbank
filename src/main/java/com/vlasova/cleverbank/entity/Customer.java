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
public class Customer {
    private long id;

    private String name;

    private String surname;

    private List<Account> accounts;

    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return new EqualsBuilder().append(id, customer.id).append(name, customer.name).append(surname, customer.surname).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(surname)
                .toHashCode();
    }
}
