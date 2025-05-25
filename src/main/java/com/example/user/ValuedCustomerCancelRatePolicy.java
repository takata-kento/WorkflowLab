package com.example.user;

import com.example.transaction.TransactionRecord;

public class ValuedCustomerCancelRatePolicy implements GradePolicy {
    private final double MAX_CANCEL_RATE = 0.001;
    private final TransactionRecord transactionRecord;
    private final User user;

    public ValuedCustomerCancelRatePolicy(TransactionRecord transactionRecord, User user) {
        this.transactionRecord = transactionRecord;
        this.user = user;
    }

    public boolean isOk() {
        long totalTransactions = transactionRecord.get().stream()
                .filter(transaction -> transaction.getUser().equals(user))
                .count();

        long canceledTransactions = transactionRecord.get().stream()
                .filter(transaction -> transaction.getUser().equals(user) && transaction.isCanceled())
                .count();

        if (totalTransactions == 0) return true;

        double cancelRate = (double) canceledTransactions / totalTransactions;
        return cancelRate <= MAX_CANCEL_RATE;
    }
}
