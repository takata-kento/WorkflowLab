package com.example.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TransactionRecord {
    private final List<Transaction> transactions;

    public TransactionRecord() {
        this.transactions = new ArrayList<>();
    }

    public List<Transaction> get() {
        return List.copyOf(this.transactions);
    }

    public TransactionRecord add(Transaction transaction) {
        this.transactions.add(transaction);
        return this;
    }

    public TransactionRecord cancelByTransactionId(String transactionId) {
        int targetIndex = this.transactions.stream()
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList())
                .indexOf(transactionId);
        
        this.transactions.set(targetIndex, this.transactions.get(targetIndex).cancel());
        return this;
    }
}
