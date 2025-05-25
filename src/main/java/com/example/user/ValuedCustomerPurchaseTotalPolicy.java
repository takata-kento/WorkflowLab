package com.example.user;

import com.example.transaction.Transaction;
import com.example.transaction.TransactionRecord;

class ValuedCustomerPurchaseTotalPolicy implements GradePolicy {
    private final TransactionRecord transactionRecord;
    private final User user;
    private final int PURCHASE_BORDER = 100000;

    ValuedCustomerPurchaseTotalPolicy(TransactionRecord transactionRecord, User user) {
        this.transactionRecord = transactionRecord;
        this.user = user;
    }

    public boolean isOk() {
        return transactionRecord.get().stream()
                .filter(transaction -> transaction.getUser() == this.user)
                .mapToInt(Transaction::total)
                .sum() >= PURCHASE_BORDER;
    }
}
