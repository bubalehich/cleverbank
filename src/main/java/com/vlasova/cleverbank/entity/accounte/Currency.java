package com.vlasova.cleverbank.entity.accounte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Currency {
    private long id;

    private String handle;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return new EqualsBuilder().append(id, currency.id).append(handle, currency.handle).append(name, currency.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(handle)
                .append(name)
                .toHashCode();
    }
}
