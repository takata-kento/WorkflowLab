package com.example.user;

import java.util.List;

import com.example.transaction.TransactionRecord;

public class User {
    private final String id;
    private final String name;
    private final int age;
    private final Gender gender;

    public User(String id, String name, int age, Gender gender) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        if (id.isEmpty()) throw new IllegalArgumentException("id cannot be empty");
        if (name == null) throw new IllegalArgumentException("name cannot be null");
        if (name.isEmpty()) throw new IllegalArgumentException("name cannot be empty");
        if (age < 0) throw new IllegalArgumentException("age cannot be negative");
        if (gender == null) throw new IllegalArgumentException("gender cannot be null");

        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public boolean isGoldUser(TransactionRecord transactionRecord) {
        List<GradePolicy> policies = List.of(
                new ValuedCustomerCancelRatePolicy(transactionRecord, this),
                new ValuedCustomerPurchaseFrequencyPolicy(transactionRecord, this),
                new ValuedCustomerPurchaseTotalPolicy(transactionRecord, this)
        );

        return policies.stream().allMatch(GradePolicy::isOk);
    }
}
