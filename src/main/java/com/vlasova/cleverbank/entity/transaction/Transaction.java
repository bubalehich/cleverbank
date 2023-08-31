package com.vlasova.cleverbank.entity.transaction;

import com.vlasova.cleverbank.entity.accounte.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    private long id;

    private UUID number;

    private Instant date = Instant.now();

    private BigDecimal amount;

    private Account sender;

    private Account receiver;

    private TransactionType transactionType;
    private String payload;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return new EqualsBuilder().append(id, that.id).append(number, that.number).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(number).toHashCode();
    }
}
