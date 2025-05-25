package com.example.transaction;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.user.User;

public class Transaction {
    private final double TAXRATE = 1.1;
    private final String transactionId;
    private final List<TransactionUnit> detail;
    private final User user;
    private final ZonedDateTime transactionDate;
    private final boolean canceled;

    public Transaction(String transactionId, User user, ZonedDateTime transactionDate) {
        this(transactionId, List.of(), user, transactionDate, false);
    }

    private Transaction(String transactionId, List<TransactionUnit> detail, User user, ZonedDateTime transactionDate, boolean canceled) {
        this.transactionId = transactionId;
        this.detail = detail;
        this.user = user;
        this.transactionDate = transactionDate;
        this.canceled = canceled;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public List<TransactionUnit> getDetail() {
        return this.detail;
    }

    public User getUser() {
        return this.user;
    }

    public ZonedDateTime getTransactionDate() {
        return this.transactionDate;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public Transaction add(Item item) {
        TransactionUnit newUnit = new TransactionUnit(this.transactionId, this.detail.size() + 1, item, 1);
        List<TransactionUnit> newDetail = Stream.concat(this.detail.stream(), Stream.of(newUnit)).collect(Collectors.toUnmodifiableList());

        return new Transaction(this.transactionId, newDetail, this.user, this.transactionDate, false);
    }

    public Transaction add(Item item, int quantity) {
        TransactionUnit newUnit = new TransactionUnit(this.transactionId, this.detail.size() + 1, item, quantity);
        List<TransactionUnit> newDetail = Stream.concat(this.detail.stream(), Stream.of(newUnit)).collect(Collectors.toUnmodifiableList());

        return new Transaction(this.transactionId, newDetail, this.user, this.transactionDate, false);
    }

    public Transaction rowCancel(int index) {
        List<TransactionUnit> newDetail = this.detail.stream()
            .map(transactionUnit -> {
                if (transactionUnit.getIndex() == index) return transactionUnit.cancel();
                return transactionUnit;
            })
            .collect(Collectors.toUnmodifiableList());

        return new Transaction(this.transactionId, newDetail, this.user, this.transactionDate, false);
    }

    public int subTotal() {
        return this.detail.stream()
            .mapToInt(
                transactionUnit -> transactionUnit.getItem().price() * transactionUnit.getQuantity()
            ).sum();
    }

    public int total() {
        return (int) (this.detail.stream()
            .mapToInt(
                transactionUnit -> transactionUnit.getItem().price() * transactionUnit.getQuantity()
            ).sum() * TAXRATE);
    }

    public Transaction cancel() {
        return new Transaction(this.transactionId, this.detail, this.user, this.transactionDate, true);
    }
}
