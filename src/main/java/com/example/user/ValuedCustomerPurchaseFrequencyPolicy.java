package com.example.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.example.transaction.TransactionRecord;

class ValuedCustomerPurchaseFrequencyPolicy implements GradePolicy {
    private final long FREQUENCY_BORDER = 10;
    private final TransactionRecord transactionRecord;
    private final User user;

    ValuedCustomerPurchaseFrequencyPolicy(TransactionRecord transactionRecord, User user) {
        this.transactionRecord = transactionRecord;
        this.user = user;
    }

    public boolean isOk() {
        long transactionCount = transactionRecord.get().stream()
                .filter(transaction -> transaction.getUser().equals(user))
                .filter(transaction -> transaction.getTransactionDate().getMonth() == ZonedDateTime.now(ZoneId.of("UTC+09:00")).getMonth())
                .count();
        return transactionCount >= FREQUENCY_BORDER;
    }
}
